package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.entities.OrderStatus;
import com.jashu.shopping_website.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    List<Order> findOrdersByUserAndOrderStatus(User user, OrderStatus orderStatus);

    List<Order> getOrdersByUserAndOrderStatusNot(User user, OrderStatus orderStatus);

    Optional<Order> findByOrderIdAndUser(UUID orderId, User user);
}
