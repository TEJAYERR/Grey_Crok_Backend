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
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){

        ProductResponse product = productService.getProductResponseById(id);
        if(product == null)
            return new ResponseEntity<>("Product does not exist", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImageById(@PathVariable int id){

        Product product = productService.getProductById(id);

        if(product == null)
            return new ResponseEntity<>("Product does not exist", HttpStatus.NO_CONTENT
            );

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(product.getImageData());
    }
}
