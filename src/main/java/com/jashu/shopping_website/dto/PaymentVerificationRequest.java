package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerificationRequest {

    private String gatewayOrderId;
    private String gatewayPaymentId;
    private String gatewaySignature;
}