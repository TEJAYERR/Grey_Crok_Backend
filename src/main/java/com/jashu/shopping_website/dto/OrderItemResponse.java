package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItemResponse {

    String productName;
    int quantity;
    BigDecimal priceAtPurchase;

    public OrderItemResponse(OrderItem orderItem){
        this.productName = orderItem.getProduct().getProductName();
        this.quantity = orderItem.getQuantity();
        this.priceAtPurchase = orderItem.getPriceAtPurchase();
    }

}
