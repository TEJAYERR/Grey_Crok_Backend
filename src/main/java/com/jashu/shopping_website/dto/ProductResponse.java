package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Category;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.entities.SubCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class ProductResponse {

    private UUID productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private int quantity;
    private boolean available;
    private int productRating;
    private Category category;
    private SubCategory subCategory;

    public ProductResponse(){}

    public ProductResponse(UUID productId, String productName, BigDecimal productPrice, String productDescription, int quantity, boolean available, int productRating, Category category, SubCategory subCategory) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.available = available;
        this.productRating = productRating;
        this.category = category;
        this.subCategory = subCategory;
    }

    public ProductResponse(Product product) {

        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productRating = product.getProductRating();
        this.available = product.getAvailable();
        this.productRating = product.getProductRating();
        this.quantity = product.getQuantity();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();
    }
}
