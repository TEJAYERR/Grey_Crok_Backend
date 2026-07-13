package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductUpdateRequest {

    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String brand;
    private int quantity;
    private Boolean available;
    private int productRating;
}
