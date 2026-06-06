package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.*;
import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.mapper.ShipmentOrderMapper;
import com.jashu.shopping_website.repository.OrderRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private UserRepo userRepo;
    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private ShipRocketService shipRocketService;

    @Autowired
    public  void setShipRocketService(ShipRocketService shipRocketService){
        this.shipRocketService = shipRocketService;
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo){
        this.orderRepo = orderRepo;
    }

    @Autowired
    public void setProductRepo(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    //Getting User Order Accroding to the email

    public List<OrderResponse> getUserOrders(String email){

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("User Does not exist");
        }

        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orderRepo.getOrdersByUser(user)){

            OrderResponse orderResponse = new OrderResponse(order);
            orderResponse.setItems(new ArrayList<>());

            for(OrderItem orderItem : order.getOrderItems()){
                orderResponse.getItems().add(new OrderItemResponse(orderItem));
            }

            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }



    //placing order

    public OrderResponse placeOrder(String email, OrderPlaceRequest orderPlaceRequest) throws RazorpayException {

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("User Does not exist");
        }

        Order order = new Order();

        Address req = orderPlaceRequest.getAddress();

        Address copy = new Address();

        copy.setDoorNumber(req.getDoorNumber());
        copy.setStreet(req.getStreet());
        copy.setCity(req.getCity());
        copy.setDistrict(req.getDistrict());
        copy.setState(req.getState());
        copy.setPincode(req.getPincode());
        copy.setCountry(req.getCountry());

        order.setAddress(copy);

        order.setOrderDate(LocalDateTime.now());
        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(5));
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for(OrderItemRequest orderItemRequest : orderPlaceRequest.getOrderItemRequests()){

            Product product = productRepo.getByProductId(orderItemRequest.getProductId());

            if(product == null){
                throw new RuntimeException("No Product Found");
            }

            if(product.getQuantity() < orderItemRequest.getQuantity()){
                throw new RuntimeException("Not available!!");
            }

            if(orderItemRequest.getQuantity() <= 0){
                throw new RuntimeException("Invalid quantity");
            }

//            product.setQuantity(product.getQuantity() - orderItemRequest.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getProductPrice());

            orderItems.add(orderItem);

            totalAmount += orderItem.getPriceAtPurchase() * orderItem.getQuantity();
        }

        System.out.println(totalAmount);
        order.setOrderItems(orderItems);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);

        order.setTotalAmount(totalAmount);

        RazorpayClient  client = new RazorpayClient("rzp_test_SfGIn89zRbphHF", "Z4r3xGX3UK3R71acSgmpN9sz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", (int)order.getTotalAmount() * 100);
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", "order_id" + order.getOrderId());

        com.razorpay.Order razorpayOrder = client.orders.create(jsonObject);

        order.setRazorpayOrderId(razorpayOrder.get("id")); //razorpay order saving

        orderRepo.save(order);
        userRepo.save(user);
        OrderResponse orderResponse = new OrderResponse(order);
        orderResponse.setItems(new ArrayList<>());

        for(OrderItem orderItem : order.getOrderItems()) {
            orderResponse.getItems().add(new OrderItemResponse(orderItem));
        }

        return orderResponse;
    }


    //verifying the payment

    public String verifyPayment(String email, PaymentVerificationRequest paymentVerificationRequest) throws Exception{

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("User Does not exist");
        }

        Order order = orderRepo.findByRazorpayOrderId(paymentVerificationRequest.getRazorpayOrderId());

        if(order == null){
            throw new RuntimeException("No order found");
        }

//        String data = paymentVerificationRequest.getRazorpayOrderId() + "|" + paymentVerificationRequest.getRazorpayPaymentId();
//
//        String generatedSignature = hmacSha256(data, "Z4r3xGX3UK3R71acSgmpN9sz");
//
//        if(!generatedSignature.equals(paymentVerificationRequest.getRazorpaySignature())){
//            throw new RuntimeException("something gone wrong signature have been disturbed");
//        }

        if(order.getPaymentStatus() == PaymentStatus.PAID){
            return "Payment already verified";
        }

        for(OrderItem item : order.getOrderItems()){
            Product product = item.getProduct();

            if(product.getQuantity() < item.getQuantity()){
                throw new RuntimeException("Stock changed, cannot fulfill order");
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setRazorpayPaymentId(paymentVerificationRequest.getRazorpayPaymentId());

        orderRepo.save(order);

        user.getCart().getCartItems().clear();
        userRepo.save(user);

        //Shipment Creation
//        String token = shipRocketService.authenticate("mail@gmail.com", "password123");
//
//        ShipmentOrderRequest shipmentOrderRequest = ShipmentOrderMapper.map(order, user);
//
//        ShipmentOrderResponse shipmentOrderResponse = shipRocketService.createShipment(token, shipmentOrderRequest);
//
//        order.setShipmentId(shipmentOrderResponse.getShipmentId());
//        order.setCourierName(shipmentOrderResponse.getCourierName());
//        order.setAwbCode(shipmentOrderResponse.getAwbCode());
//        order.setTrackingStatus(shipmentOrderResponse.getTrackingStatus());

        return "verified successfully";
    }



    //shipment tracking

    public String updateOrderTrackingFromWebhook(ShipRocketWebhookRequest shipRocketWebhookRequest){

        Order order = orderRepo.getOrderByShipmentId(shipRocketWebhookRequest.getShipmentId());

        if(order == null){
            throw new RuntimeException("No Order exist");
        }

        order.setTrackingStatus(shipRocketWebhookRequest.getCourierStatus());

        order.setAwbCode(shipRocketWebhookRequest.getAwbCode());

        order.setCourierName(shipRocketWebhookRequest.getCourierName());

        orderRepo.save(order);

        return "updated successfully";
    }



    private static String hmacSha256(String data, String key) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
        javax.crypto.spec.SecretKeySpec secretKey =
                new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return org.apache.commons.codec.binary.Hex.encodeHexString(rawHmac);
    }



    public OrderResponse orderStatus(String email, int orderId, OrderStatusUpdate orderStatusUpdate){

        Order order = orderRepo.getOrderByOrderId(orderId);
        if(order == null){
            throw new IllegalArgumentException("order does not exist");
        }

        OrderStatus current = order.getOrderStatus();
        OrderStatus next = orderStatusUpdate.getOrderStatus();

        if(!isValidTransition(current, next)){
            throw new RuntimeException("order status cannot be changed");
        }

        order.setOrderStatus(orderStatusUpdate.getOrderStatus());
        orderRepo.save(order);

        OrderResponse orderResponse = new OrderResponse(order);
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for(OrderItem orderItem : order.getOrderItems()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem);
            orderItemResponses.add(orderItemResponse);
        }

        orderResponse.setItems(orderItemResponses);

        return orderResponse;
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next){

        if(current == OrderStatus.PENDING && (next == OrderStatus.CANCELLED || next == OrderStatus.CONFIRMED)){
            return true;
        }
        else if(current == OrderStatus.CONFIRMED && (next == OrderStatus.SHIPPED || next == OrderStatus.CANCELLED)){
            return true;
        }
        else return current == OrderStatus.SHIPPED && (next == OrderStatus.DELIVERED);
    }
}
