package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.OrderItemRequest;
import com.jashu.shopping_website.dto.OrderItemResponse;
import com.jashu.shopping_website.dto.OrderResponse;
import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.repository.OrderRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private UserRepo userRepo;
    private ProductRepo productRepo;
    private OrderRepo orderRepo;


    @Autowired
    public void setUserRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo){
        this.orderRepo = orderRepo;
    }

    @Autowired
    public void setProductRepo(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public List<OrderResponse> getUserOrders(String email){

        User user = userRepo.getByEmail(email);
        if(user == null){
            throw new IllegalArgumentException("User Does not exist");
        }

        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orderRepo.getOrdersByUser(user)){

            OrderResponse orderResponse = new OrderResponse(order);
            orderResponse.setItems(new ArrayList<>());

            for(OrderItem orderItem : order.getOrderItems()){
                orderResponse.getItems().add(new OrderItemResponse(orderItem));
            }

            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }

    public String placeOrder(String email, List<OrderItemRequest> items){

        User user = userRepo.getByEmail(email);
        if(user == null){
            throw new IllegalArgumentException("User Does not exist");
        }

        Order order = new Order();

        order.setOrderDate(LocalDateTime.now());
        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(5));
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for(OrderItemRequest orderItemRequest : items){
            Product product = productRepo.getByProductId(orderItemRequest.getProductId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getProductPrice() * orderItem.getQuantity());

            order.setOrderItems(orderItems);
            order.setOrderStatus(OrderStatus.PENDING);
            orderItems.add(orderItem);

            totalAmount += orderItem.getPriceAtPurchase();
        }

        order.setTotalAmount(totalAmount);
        orderRepo.save(order);
        userRepo.save(user);
        return "Order placed successfully";
    }
}
