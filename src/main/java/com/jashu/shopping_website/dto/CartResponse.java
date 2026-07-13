package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Cart;
import com.jashu.shopping_website.entities.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class CartResponse {

    UUID cartId;
    List<CartItemResponse> cartItems = new ArrayList<>();

    public CartResponse(Cart cart){
        this.cartId = cart.getCartId();
        for(CartItem cartItem : cart.getCartItems()){
            cartItems.add(new CartItemResponse(cartItem));
        }
    }
}
