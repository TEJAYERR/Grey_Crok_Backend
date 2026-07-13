package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Payment;
import com.jashu.shopping_website.entities.PaymentMethod;
import com.jashu.shopping_website.entities.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentResponse {

    private UUID paymentId;
    private String gatewayOrderId;
    private String gatewayName;
    private LocalDateTime paidAt;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    public PaymentResponse(Payment payment){
        this.paymentId = payment.getPaymentId();
        this.gatewayOrderId = payment.getGatewayOrderId();
        this.gatewayName = payment.getGatewayName();
        this.paidAt = payment.getPaidAt();
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod();
        this.paymentStatus = payment.getPaymentStatus();
    }
}
