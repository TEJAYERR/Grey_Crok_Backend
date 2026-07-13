package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.ProductUpdateRequest;
import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminProductController {

    final private ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                        @RequestPart Product product,
                                        @RequestPart MultipartFile productImage) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.productService.addProduct(
                        userPrinciple.getUser().getUserId(), product, productImage));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @PathVariable UUID productId,
                                           @RequestPart Product product,
                                           @RequestPart MultipartFile imageFile) throws IOException {

            return ResponseEntity.ok(this.productService.updateProduct(userPrinciple.getUser().getUserId(), productId, product, imageFile));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @PathVariable UUID productId){

        return ResponseEntity.ok(productService.deleteProduct(userPrinciple.getUser().getUserId(), productId));
    }
}
