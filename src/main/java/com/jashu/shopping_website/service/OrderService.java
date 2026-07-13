package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.*;
import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.exception.*;
import com.jashu.shopping_website.repository.*;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;
    private final RazorpayService razorpayService;
    private final OrderCreationService orderCreationService;
    private final PaymentService paymentService;

    public OrderService(UserRepo userRepo, OrderRepo orderRepo, PaymentRepo paymentRepo, RazorpayService razorpayService, OrderCreationService orderCreationService, PaymentService paymentService) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
        this.razorpayService = razorpayService;
        this.orderCreationService = orderCreationService;
        this.paymentService = paymentService;
    }


    //Getting User Order according to the user -> userId
    @Transactional
    public List<OrderResponse> getUserOrders(UUID userId){

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        return orderRepo.getOrdersByUserAndOrderStatusNot(user, OrderStatus.PENDING)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    @Transactional
    public OrderResponse getUserOrderById(UUID userId, UUID orderId){

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        Order order = orderRepo.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("order not found !"));

        Payment payment = paymentRepo
                .findByOrderAndPaymentStatusNot(order,
                        PaymentStatus.PENDING
                );

        return new OrderResponse(order, payment);
    }

    //placing order
    public OrderResponse placeOrder(UUID userId, OrderPlaceRequest orderPlaceRequest) throws RazorpayException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Payment payment = orderCreationService.createOrder(user, orderPlaceRequest);
        Order order = payment.getOrder();

        if(payment.getGatewayOrderId() == null && orderPlaceRequest.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {

            //razorpay order creation
            com.razorpay.Order razorpayOrder = razorpayService.createRazorpayOrder(order);

            System.out.println("\n update details \n");
            payment = paymentService.updatePaymentDetails(payment.getPaymentId(),
                    "Razorpay",
                    razorpayOrder.get("id"),
                    PaymentStatus.PENDING,
                    PaymentMethod.RAZORPAY
            );
        }

        else if(orderPlaceRequest.getPaymentMethod().equals(PaymentMethod.COD)){

            System.out.println("\n update details \n");
            payment = paymentService.updatePaymentDetails(payment.getPaymentId(),
                    null,
                    null,
                    PaymentStatus.NOT_PAID,
                    PaymentMethod.COD
            );

            orderCreationService.decrementStock(order);
            orderCreationService.clearCart(user);
        }

        order = payment.getOrder();

        order = orderCreationService.setOrderAddress(order, orderPlaceRequest.getAddress());

        OrderResponse orderResponse = new OrderResponse(order, payment);
        orderResponse.setItems(new ArrayList<>());

        for(OrderItem orderItem : order.getOrderItems()) {
            orderResponse.getItems().add(new OrderItemResponse(orderItem));
        }

        return orderResponse;
    }


    //verifying the payment
    @Transactional
    public String verifyPayment(UUID userId, PaymentVerificationRequest paymentVerificationRequest) throws Exception{

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        Payment payment = paymentRepo.findByGatewayOrderId(paymentVerificationRequest.getGatewayOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("payment not found"));

        Order order = payment.getOrder();

        if(!user.equals(order.getUser())){
            throw new UnauthorizedOperationException("not authorized");
        }

        String data = paymentVerificationRequest.getGatewayOrderId() + "|" + paymentVerificationRequest.getGatewayPaymentId();
        String generatedSignature = razorpayService.generateSignature(data);

        if(!generatedSignature.equals(paymentVerificationRequest.getGatewaySignature())){
            throw new InvalidSignatureException("something gone wrong signature have been disturbed");
        }

        if(order.getPaymentStatus() == PaymentStatus.PAID){
            throw new DuplicateOperationException("payment already verified for the order!");
        }

        orderCreationService.decrementStock(order);

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PAID);

        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        payment.setGatewayPaymentId(paymentVerificationRequest.getGatewayPaymentId());
        payment.setGatewaySignature(paymentVerificationRequest.getGatewaySignature());

        orderRepo.save(order);
        orderCreationService.clearCart(user);

        return "verified successfully";
    }

    public OrderResponse cancelOrder(UUID userId, UUID orderId) throws RazorpayException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        Order order = orderRepo.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("order not found !"));

        if(!order.getOrderStatus().equals(OrderStatus.CANCELLED)){
            throw new DuplicateOperationException("order cancelled already !");
        }

        //order which cannot be canceled
        if(!order.getOrderStatus().equals(OrderStatus.CONFIRMED)){
            throw new BadRequestException("order cannot be cancelled !");
        }

        Payment payment = paymentRepo.findByOrderAndPaymentStatusNot(
                order,
                PaymentStatus.PENDING
        );

        if(!order.getPaymentMethod().equals(PaymentMethod.COD)){
            //initiate refund
            String gateway = payment.getGatewayName();
            String getGatewayPaymentId = payment.getGatewayPaymentId();

            if(gateway.equals("Razorpay")){

                Refund refund = razorpayService.refund(getGatewayPaymentId);
                orderCreationService.updateOrderAndPaymentDetails(order, payment, refund);
            }
        }

        if(order.getPaymentMethod().equals(PaymentMethod.COD)){
            orderCreationService.updateOrderAndPaymentDetails(order, payment, null);
        }

        orderCreationService.restoreStock(order, payment);

        return new OrderResponse(order);
    }



    //shipment tracking
//    public String updateOrderTrackingFromWebhook(ShipRocketWebhookRequest shipRocketWebhookRequest){
//
//        Shipment shipment = shipmentRepo.findById(shipRocketWebhookRequest.getShipmentId())
//                .orElseThrow();
//
//        if(order == null){
//            throw new RuntimeException("No Order exist");
//        }
//
//        order.setTrackingStatus(shipRocketWebhookRequest.getCourierStatus());
//
//        order.setAwbCode(shipRocketWebhookRequest.getAwbCode());
//
//        order.setCourierName(shipRocketWebhookRequest.getCourierName());
//
//        orderRepo.save(order);
//
//        return "updated successfully";
//    }





//    public OrderResponse orderStatus(String email, UUID orderId, OrderStatusUpdate orderStatusUpdate){
//
//        Order order = orderRepo.getOrderByOrderId(orderId);
//        if(order == null){
//            throw new IllegalArgumentException("order does not exist");
//        }
//
//        OrderStatus current = order.getOrderStatus();
//        OrderStatus next = orderStatusUpdate.getOrderStatus();
//
//        if(!isValidTransition(current, next)){
//            throw new RuntimeException("order status cannot be changed");
//        }
//
//        order.setOrderStatus(orderStatusUpdate.getOrderStatus());
//        orderRepo.save(order);
//
//        OrderResponse orderResponse = new OrderResponse(order, new Payment());
//        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
//
//        for(OrderItem orderItem : order.getOrderItems()){
//            OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem);
//            orderItemResponses.add(orderItemResponse);
//        }
//
//        orderResponse.setItems(orderItemResponses);
//
//        return orderResponse;
//    }
//
//    private boolean isValidTransition(OrderStatus current, OrderStatus next){
//
//        if(current == OrderStatus.PENDING && (next == OrderStatus.CANCELLED || next == OrderStatus.CONFIRMED)){
//            return true;
//        }
//        else if(current == OrderStatus.CONFIRMED && (next == OrderStatus.SHIPPED || next == OrderStatus.CANCELLED)){
//            return true;
//        }
//        else return current == OrderStatus.SHIPPED && (next == OrderStatus.DELIVERED);
//    }
}
