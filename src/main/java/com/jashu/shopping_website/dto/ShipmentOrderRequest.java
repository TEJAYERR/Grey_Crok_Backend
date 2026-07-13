package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ShipmentOrderRequest {

    UUID orderId;
    LocalDateTime orderDate;
    String pickupLocation;

    String billingCustomerName;
    String billingAddress;
    String billingIsdCode;
    String billingCity;
    String billingPincode;
    String billingState;
    String billingCountry;
    String billingEmail;
    String billingPhone;
    boolean shippingIsBilling;

    List<ShippingOrderItem> shippingOrderItems;
    int length;
    int breadth;
    int height;
    int weight;
}
