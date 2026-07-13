package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CartItemResponse {

    UUID cartItemId;
    UUID productId;
    int quantity;

    public CartItemResponse(){}

    public CartItemResponse(CartItem cartItem) {
        this.cartItemId = cartItem.getCartItemId();
        this.productId = cartItem.getProduct().getProductId();
        this.quantity = cartItem.getQuantity();
    }
}
