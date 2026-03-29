package com.jashu.shopping_website.dto;

public class ProductUpdateRequest {

    private String productName;
    private float productPrice;
    private String productDescription;
    private String brand;
    private int quantity;
    private Boolean available;
    private int productRating;

    public ProductUpdateRequest(String productName, float productPrice, String productDescription, String brand, int quantity, boolean available, int productRating) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.brand = brand;
        this.quantity = quantity;
        this.available = available;
        this.productRating = productRating;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

}
