package com.jashu.shopping_website.entities;

import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private float productPrice;
    private String productDescription;
    private String brand;
    private int quantity;
    private Boolean available;
    private int productRating;
    private String imageName;
    private String imageType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @OneToMany(mappedBy = "product")
    List<CartItem> cartItems;

    public Product(){}

    public Product(Integer productId, String productName, float productPrice, String productDescription, String brand, int quantity, boolean available, int productRating, String imageName, String imageType, byte[] imageData) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.brand = brand;
        this.quantity = quantity;
        this.available = available;
        this.productRating = productRating;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


}
