package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
}
