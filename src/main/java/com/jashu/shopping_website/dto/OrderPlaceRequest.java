package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Address;

import java.util.List;

public class OrderPlaceRequest {

    List<OrderItemRequest> orderItemRequests;
    Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItemRequest> getOrderItemRequests() {
        return orderItemRequests;
    }

    public void setOrderItemRequests(List<OrderItemRequest> orderItemRequests) {
        this.orderItemRequests = orderItemRequests;
    }
}
