package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.ProductUpdateRequest;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.service.ProductService;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminProductController {

    final private ProductService productService;

    @Autowired
    AdminProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile productImage, @RequestHeader("Authorization") String authorization){

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

        if(role.equalsIgnoreCase("admin")){
            try {
                return new ResponseEntity<>(this.productService.addProduct(product, productImage), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestHeader("Authorization") String authorization, @RequestPart ProductUpdateRequest productUpdateRequest, @RequestPart MultipartFile imageFile){

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

        if(role.equalsIgnoreCase("admin")){
            try {
                return new ResponseEntity<>(this.productService.updateProduct(id, productUpdateRequest, imageFile), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id, @RequestHeader("Authorization") String authorization){

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

        if(role.equalsIgnoreCase("admin")){
            try {
                return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }
}
