package com.jashu.shopping_website.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID cartItemId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    Cart cart;

    int quantity;
}
