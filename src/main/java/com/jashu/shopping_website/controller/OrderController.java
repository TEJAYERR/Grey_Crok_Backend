package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.OrderItemRequest;
import com.jashu.shopping_website.service.OrderService;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(@RequestHeader("Authorization") String authorization){

        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new RuntimeException("Invalid Token!");
        }

        String token = authorization.substring(7);
        String[] data = TokenUtil.decodeToken(token);

        if(data.length < 2){
            throw new RuntimeException("Misformed token");
        }

        String email = data[0];
        String role = data[1];

        if(!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            System.out.println("Entered the order controller");
            System.out.println(orderService.getUserOrders(email));
            return new ResponseEntity<>(orderService.getUserOrders(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("placeOrder")
    public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String authorization, @RequestBody List<OrderItemRequest> items){

        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new RuntimeException("Invalid Token!");
        }

        String token = authorization.substring(7);
        String[] data = TokenUtil.decodeToken(token);

        if(data.length < 2){
            throw new RuntimeException("Misformed token");
        }

        String email = data[0];
        String role = data[1];

        if(!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            System.out.println("Entered the order controller");
            return new ResponseEntity<>(orderService.placeOrder(email, items), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
