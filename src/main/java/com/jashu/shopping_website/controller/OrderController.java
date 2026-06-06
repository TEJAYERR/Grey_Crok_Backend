package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.OrderPlaceRequest;
import com.jashu.shopping_website.dto.OrderStatusUpdate;
import com.jashu.shopping_website.dto.PaymentVerificationRequest;
import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.service.OrderService;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            return new ResponseEntity<>(orderService.getUserOrders(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String authorization, @RequestBody OrderPlaceRequest orderPlaceRequest){

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
            return new ResponseEntity<>(orderService.placeOrder(email, orderPlaceRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payment-verification")
    public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String authorization, @RequestBody PaymentVerificationRequest paymentVerificationRequest){

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
            return new ResponseEntity<>(Map.of("message", orderService.verifyPayment(email, paymentVerificationRequest)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{orderId}/orderstatus")
    public ResponseEntity<?> orderStatus(@RequestHeader("Authorization") String authorization, @PathVariable int orderId, @RequestBody OrderStatusUpdate orderStatusUpdate){

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

        if(!role.equalsIgnoreCase("admin")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return new ResponseEntity<>(orderService.orderStatus(email, orderId, orderStatusUpdate), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
