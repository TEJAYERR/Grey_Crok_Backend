package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import com.jashu.shopping_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class UserProductController {

    ProductService productService;

    @Autowired
    UserProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable UUID productId){

        return ResponseEntity.ok(productService.getProductResponseById(productId));
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<?> getImageById(@PathVariable UUID productId){

        Product product = productService.getProductById(productId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(product.getImageData());
    }
}
