package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.entities.CartItem;
import com.jashu.shopping_website.service.CartService;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(@RequestHeader("Authorization") String authorization){

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
            return new ResponseEntity<>(cartService.getCartItems(email), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addProductToCart(@RequestHeader("Authorization") String authorization, @PathVariable int id){

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
            return new ResponseEntity<>(cartService.addProductToCart(id, email), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductFromCart(@RequestHeader("Authorization") String authorization, @PathVariable int id){

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
            return new ResponseEntity<>(cartService.deleteProductFromCart(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<?> ReduceQuantityOfProduct(@RequestHeader("Authorization") String authorization, @PathVariable int id){
//
//        if(authorization == null || !authorization.startsWith("Bearer ")){
//            throw new RuntimeException("Invalid Token!");
//        }
//
//        String token = authorization.substring(7);
//        String[] data = TokenUtil.decodeToken(token);
//
//        if(data.length < 2){
//            throw new RuntimeException("Misformed token");
//        }
//
//        String email = data[0];
//        String role = data[1];
//
//        if(!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user")){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        try {
//            return new ResponseEntity<>(cartService.deleteProductFromCart(id, email), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
