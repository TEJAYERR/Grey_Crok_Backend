package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByUser(User user);

    List<Order> getOrdersByUserEmail(String userEmail);
}
