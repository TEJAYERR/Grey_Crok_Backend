package com.jashu.shopping_website.entities;

public enum PaymentStatus {
    PENDING,
    NOT_PAID,
    PROCESSING,
    PAID,
    FAILED,
    ABANDONED,

    REFUND_INITIATED,
    REFUNDED
}
