package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.OrderItemRequest;
import com.jashu.shopping_website.dto.OrderPlaceRequest;
import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.exception.BadRequestException;
import com.jashu.shopping_website.exception.OutOfStockException;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.OrderRepo;
import com.jashu.shopping_website.repository.PaymentRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import com.razorpay.Refund;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderCreationService {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;

    public OrderCreationService(UserRepo userRepo, ProductRepo productRepo, OrderRepo orderRepo, PaymentRepo paymentRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    public Payment createOrder(User user, OrderPlaceRequest orderPlaceRequest) {

        List<Order> pendingOrders = orderRepo.findOrdersByUserAndOrderStatus(user, OrderStatus.PENDING);

        // Fetch products in one single batch query to avoid N+1 issues
        Set<UUID> productIds = orderPlaceRequest.getOrderItemRequests().stream()
                .map(OrderItemRequest::getProductId)
                .collect(Collectors.toSet());

        List<Product> products = productRepo.findAllById(productIds);
        Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductId, p -> p));

        for (Order pendingOrder : pendingOrders) {
            if (isSameCheckout(pendingOrder, orderPlaceRequest, productMap)) {

                Payment oldPayment = paymentRepo.findByOrderAndPaymentMethodAndPaymentStatus(
                        pendingOrder,
                        orderPlaceRequest.getPaymentMethod(),
                        PaymentStatus.PENDING);

                if (oldPayment != null) {

                    oldPayment.setRetryCount(oldPayment.getRetryCount() + 1);
                    return paymentRepo.save(oldPayment);
                }

                Payment freshPaymentMethod = new Payment();
                freshPaymentMethod.setAmount(pendingOrder.getTotalAmount());
                freshPaymentMethod.setOrder(pendingOrder);
                freshPaymentMethod.setPaymentStatus(PaymentStatus.PENDING);
                freshPaymentMethod.setPaymentMethod(orderPlaceRequest.getPaymentMethod());
                freshPaymentMethod.setRetryCount(1); // First time hitting this method choice

                pendingOrder.getPayments().add(freshPaymentMethod);

                return paymentRepo.save(freshPaymentMethod);
            }
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(5));
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest orderItemRequest : orderPlaceRequest.getOrderItemRequests()) {
            Product product = productMap.get(orderItemRequest.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("product not found!");
            }

            if (orderItemRequest.getQuantity() <= 0) {
                throw new BadRequestException("invalid quantity");
            }

            if (product.getQuantity() < orderItemRequest.getQuantity()) {
                throw new OutOfStockException("not available !");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getProductPrice());

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(
                    orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
        }

        order.setOrderItems(orderItems);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);

        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setOrder(order);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(orderPlaceRequest.getPaymentMethod());

        System.out.println("\nend\n");

        orderRepo.save(order);

        return paymentRepo.save(payment);
    }

    @Transactional
    public Order setOrderAddress(Order order, Address newAddress) {

        Address copy = new Address();
        copy.setDoorNumber(newAddress.getDoorNumber());
        copy.setStreet(newAddress.getStreet());
        copy.setCity(newAddress.getCity());
        copy.setDistrict(newAddress.getDistrict());
        copy.setState(newAddress.getState());
        copy.setPincode(newAddress.getPincode());
        copy.setCountry(newAddress.getCountry());

        order.setAddress(copy);
        return orderRepo.save(order);
    }

    @Transactional
    public void clearCart(User user){

        if(user.getCart() != null &&
                user.getCart().getCartItems() != null){

            user.getCart().getCartItems().clear();
        }
    }

    private boolean isSameCheckout(Order pendingOrder, OrderPlaceRequest request, Map<UUID, Product> productMap) {
        List<OrderItem> orderItems = pendingOrder.getOrderItems();
        List<OrderItemRequest> requestItems = request.getOrderItemRequests();

        if (orderItems.size() != requestItems.size()) {
            return false;
        }

        Map<UUID, Integer> requestedQuantities = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest item : requestItems) {
            Product product = productMap.get(item.getProductId());
            if (product == null) {
                return false;
            }
            requestedQuantities.put(item.getProductId(), item.getQuantity());
            totalAmount = totalAmount.add(
                    product.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        if (pendingOrder.getTotalAmount().compareTo(totalAmount) != 0) {
            return false;
        }

        for (OrderItem orderItem : orderItems) {
            Integer quantity = requestedQuantities.get(orderItem.getProduct().getProductId());

            if (quantity == null) {
                return false;
            }
            // Objects.equals avoids primitive object reference mismatches
            if (!Objects.equals(quantity, orderItem.getQuantity())) {
                return false;
            }
            if (orderItem.getPriceAtPurchase().compareTo(orderItem.getProduct().getProductPrice()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void decrementStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();

            if (product.getQuantity() < orderItem.getQuantity()) {
                throw new OutOfStockException("Stock changed, cannot fulfill order");
            }

            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            if (product.getQuantity() < 1) {
                product.setAvailable(false);
            }
            productRepo.save(product);
        }
    }

    @Transactional
    protected void updateOrderAndPaymentDetails(Order order,
                                                Payment payment,
                                                Refund refund) {

        if (refund != null) {
            payment.setGatewayRefundId(refund.get("id"));
            payment.setPaymentStatus(PaymentStatus.REFUND_INITIATED);

            order.setPaymentStatus(PaymentStatus.REFUND_INITIATED);
        } else {
            payment.setPaymentStatus(PaymentStatus.NOT_PAID);

            order.setPaymentStatus(PaymentStatus.NOT_PAID);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setExpectedDeliveryDate(null);
    }

    @Transactional
    public void restoreStock(Order order, Payment payment) {

        for(OrderItem orderItem : order.getOrderItems()){

            Product product = orderItem.getProduct();
            product.setQuantity(product.getQuantity() + orderItem.getQuantity());
            if(product.getQuantity() > 0){
                product.setAvailable(true);
            }
        }
    }
}
