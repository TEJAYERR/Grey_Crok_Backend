package com.jashu.shopping_website.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems;

    public Cart(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", cartItems=" + cartItems +
                '}';
    }
}
