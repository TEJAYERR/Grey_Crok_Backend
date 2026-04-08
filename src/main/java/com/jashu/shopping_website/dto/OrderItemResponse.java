package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.OrderItem;

public class OrderItemResponse {

    int orderId;
    String orderName;
    int quantity;
    double priceAtPurchase;

    public OrderItemResponse(OrderItem orderItem){
        this.orderId = orderItem.getOrderItemId();
        this.orderName = orderItem.getProduct().getProductName();
        this.quantity = orderItem.getQuantity();
        this.priceAtPurchase = orderItem.getPriceAtPurchase();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
