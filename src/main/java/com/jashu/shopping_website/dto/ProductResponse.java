package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Product;
import org.springframework.stereotype.Component;

public class ProductResponse {

    private int productId;
    private String productName;
    private float productPrice;
    private String productDescription;
    private int quantity;
    private boolean isAvailable;
    private int productRating;

    public ProductResponse(){}

    public ProductResponse(int productId, String productName, float productPrice, String productDescription, int quantity, boolean isAvailable, int productRating) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.productRating = productRating;
    }

    public static ProductResponse getProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDescription(product.getProductDescription());
        productResponse.setProductRating(product.getProductRating());
        productResponse.setAvailable(product.isAvailable());
        productResponse.setProductRating(product.getProductRating());
        productResponse.setQuantity(product.getQuantity());
        return productResponse;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }
}
