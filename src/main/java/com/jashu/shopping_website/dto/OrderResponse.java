package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Order;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    LocalDateTime orderDate;
    LocalDateTime expectedDeliveryDate;
    LocalDateTime deliverdDate;

    private int orderId;
    private double totalAmount;
    private List<OrderItemResponse> items;

    public OrderResponse(Order order) {
        this.orderDate = order.getOrderDate();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.deliverdDate = order.getDeliverdDate();
        this.orderId = order.getOrderId();
        this.totalAmount = order.getTotalAmount();
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

    public LocalDateTime getDeliverdDate() {
        return deliverdDate;
    }

    public void setDeliverdDate(LocalDateTime deliverdDate) {
        this.deliverdDate = deliverdDate;
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
