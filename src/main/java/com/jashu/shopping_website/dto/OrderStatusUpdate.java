package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.OrderStatus;

public class OrderStatusUpdate {

    OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
