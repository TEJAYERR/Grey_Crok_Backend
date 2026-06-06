package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.CartItem;

import java.util.List;

public class CartResponse {

    int cartId;
    List<CartItemResponse> cartItems;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }
}
