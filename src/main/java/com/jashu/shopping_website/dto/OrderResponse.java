package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.entities.OrderStatus;
import com.jashu.shopping_website.entities.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    String razorpayOrderId;
    LocalDateTime orderDate;
    LocalDateTime expectedDeliveryDate;
    LocalDateTime deliveredDate;
    OrderStatus orderStatus;

    private int orderId;
    private double totalAmount;
    private List<OrderItemResponse> items;
    PaymentStatus paymentStatus;

    public OrderResponse(Order order) {
        this.orderDate = order.getOrderDate();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.deliveredDate = order.getDeliveredDate();
        this.orderId = order.getOrderId();
        this.totalAmount = order.getTotalAmount();
        this.orderStatus = order.getOrderStatus();
        this.razorpayOrderId = order.getRazorpayOrderId();
        this.orderStatus = order.getOrderStatus();
        this.paymentStatus = order.getPaymentStatus();
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
