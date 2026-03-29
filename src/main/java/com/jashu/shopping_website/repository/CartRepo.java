package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
