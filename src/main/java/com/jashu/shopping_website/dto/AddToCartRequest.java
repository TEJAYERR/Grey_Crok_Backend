package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AddToCartRequest {
    UUID productId;
    int quantity;
}
