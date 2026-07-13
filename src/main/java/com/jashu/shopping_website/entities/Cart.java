package com.jashu.shopping_website.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID cartId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems;
}
