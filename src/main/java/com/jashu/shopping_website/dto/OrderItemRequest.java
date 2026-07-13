package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class OrderItemRequest {

    UUID productId;
    int quantity;
}
