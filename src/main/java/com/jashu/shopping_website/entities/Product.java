package com.jashu.shopping_website.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private int quantity;
    private Boolean available;
    private int productRating;
    private String imageName;
    private String imageType;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @OneToMany(mappedBy = "product")
    List<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", quantity=" + quantity +
                ", available=" + available +
                ", productRating=" + productRating +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", category=" + category +
                ", subCategory=" + subCategory +
                '}';
    }
}
