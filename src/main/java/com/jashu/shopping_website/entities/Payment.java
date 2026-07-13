package com.jashu.shopping_website.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID paymentId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private String gatewayName;
    private String gatewaySignature;
    private LocalDateTime paidAt;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    int retryCount;

    //refund details
    String gatewayRefundId;

    LocalDateTime refundedTime;

    public Payment(){}

    public Payment(Payment previous){
        this.order = previous.getOrder();
        this.gatewayOrderId = previous.gatewayOrderId;
        this.gatewayName = previous.getGatewayName();
        this.amount = previous.getAmount();
        this.paymentMethod = previous.getPaymentMethod();
        this.paymentStatus = previous.getPaymentStatus();
    }
}
