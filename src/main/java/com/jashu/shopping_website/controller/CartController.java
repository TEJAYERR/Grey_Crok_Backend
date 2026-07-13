package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.AddToCartRequest;
import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(@AuthenticationPrincipal UserPrinciple userPrinciple) {

        return ResponseEntity.ok(cartService.getCartItems(userPrinciple.getUser().getUserId()));
    }

    @PostMapping("/items")
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                              @RequestBody AddToCartRequest addToCartRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addProductToCart(userPrinciple.getUser().getUserId(), addToCartRequest));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<?> deleteProductFromCart(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @PathVariable UUID cartItemId) {

        return ResponseEntity.ok(Map.of("message", cartService.deleteProductFromCart(userPrinciple.getUser().getUserId(), cartItemId)));
    }
}