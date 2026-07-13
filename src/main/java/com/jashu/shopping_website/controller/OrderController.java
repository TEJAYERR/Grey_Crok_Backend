package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.OrderPlaceRequest;
import com.jashu.shopping_website.dto.OrderStatusUpdate;
import com.jashu.shopping_website.dto.PaymentVerificationRequest;
import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(@AuthenticationPrincipal UserPrinciple userPrinciple){

        return ResponseEntity.ok(orderService.getUserOrders(userPrinciple.getUser().getUserId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getUserOrders(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable UUID orderId){

        return ResponseEntity.ok(orderService
                .getUserOrderById(userPrinciple.getUser().getUserId(), orderId));
    }


    @PostMapping
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                        @RequestBody OrderPlaceRequest orderPlaceRequest) throws RazorpayException {

        return ResponseEntity.ok(orderService.placeOrder(userPrinciple.getUser().getUserId(), orderPlaceRequest));
    }

    @PostMapping("/payment-verification")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                        @RequestBody PaymentVerificationRequest paymentVerificationRequest) throws Exception {

        return ResponseEntity.ok(Map.of("message", orderService.verifyPayment(userPrinciple.getUser().getUserId(), paymentVerificationRequest)));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                         @PathVariable UUID orderId) throws RazorpayException {

        return ResponseEntity
                .ok(orderService.cancelOrder(userPrinciple.getUser().getUserId(), orderId));
    }



//    @PatchMapping("/{orderId}/orderstatus")
//    public ResponseEntity<?> orderStatus(@AuthenticationPrincipal UserPrinciple userPrinciple,
//                                         @PathVariable UUID orderId,
//                                         @RequestBody OrderStatusUpdate orderStatusUpdate){
//
//        return ResponseEntity.ok(orderService.orderStatus(userPrinciple.getUser().getEmail(), orderId, orderStatusUpdate));
//    }

}
