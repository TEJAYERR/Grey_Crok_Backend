package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {

    private UUID orderId;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;

    private LocalDateTime orderDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime deliveredDate;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

    private Address address;

    private PaymentResponse paymentResponse;

    public OrderResponse(Order order) {
        this.orderDate = order.getOrderDate();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.deliveredDate = order.getDeliveredDate();
        this.orderId = order.getOrderId();
        this.totalAmount = order.getTotalAmount();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getAddress();
        this.paymentStatus = order.getPaymentStatus(); // straight from Order, no Payment join needed
    }

    public OrderResponse(Order order, Payment payment) {
        this.orderId = order.getOrderId();
        this.totalAmount = order.getTotalAmount();
        this.items = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            this.items.add(new OrderItemResponse(orderItem));
        }
        this.address = order.getAddress();
        this.orderDate = order.getOrderDate();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.deliveredDate = order.getDeliveredDate();
        this.orderStatus = order.getOrderStatus();
        this.paymentStatus = payment.getPaymentStatus();
        this.paymentResponse = new PaymentResponse(payment);
    }
}
