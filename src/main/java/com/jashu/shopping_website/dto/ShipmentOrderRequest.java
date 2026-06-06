package com.jashu.shopping_website.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ShipmentOrderRequest {

    int orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getBillingCustomerName() {
        return billingCustomerName;
    }

    public void setBillingCustomerName(String billingCustomerName) {
        this.billingCustomerName = billingCustomerName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingIsdCode() {
        return billingIsdCode;
    }

    public void setBillingIsdCode(String billingIsdCode) {
        this.billingIsdCode = billingIsdCode;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingPincode() {
        return billingPincode;
    }

    public void setBillingPincode(String billingPincode) {
        this.billingPincode = billingPincode;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public List<ShippingOrderItem> getShippingOrderItems() {
        return shippingOrderItems;
    }

    public void setShippingOrderItems(List<ShippingOrderItem> shippingOrderItems) {
        this.shippingOrderItems = shippingOrderItems;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWright() {
        return weight;
    }

    public void setWright(int wright) {
        this.weight = wright;
    }
}
