package com.jashu.shopping_website.mapper;

import com.jashu.shopping_website.dto.ShipmentOrderRequest;
import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.entities.User;

public class ShipmentOrderMapper {

    public static ShipmentOrderRequest map(Order order, User user){

        if (order == null || user == null) {
            return null;
        }

        ShipmentOrderRequest shipmentOrderRequest = new ShipmentOrderRequest();
        shipmentOrderRequest.setOrderId(order.getOrderId());
        shipmentOrderRequest.setOrderDate(order.getOrderDate());
        shipmentOrderRequest.setPickupLocation("Home");
        shipmentOrderRequest.setBillingCustomerName(user.getUserName());
        shipmentOrderRequest.setBillingPhone(user.getMobileNumber());

        return shipmentOrderRequest;
    }
}
