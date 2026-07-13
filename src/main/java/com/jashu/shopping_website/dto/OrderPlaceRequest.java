package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Address;
import com.jashu.shopping_website.entities.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderPlaceRequest {

    List<OrderItemRequest> orderItemRequests;
    Address address;
    PaymentMethod paymentMethod;
}
