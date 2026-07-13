package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Order;
import com.jashu.shopping_website.entities.Payment;
import com.jashu.shopping_website.entities.PaymentMethod;
import com.jashu.shopping_website.entities.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID>
{
    Optional<Payment> findByGatewayOrderId(String gatewayOrderId);

    Payment findByOrderAndPaymentMethodAndPaymentStatus(Order order, PaymentMethod paymentMethod, PaymentStatus paymentStatus);

    Payment findByOrderAndPaymentStatusNot(Order order, PaymentStatus paymentStatus);

    Optional<Payment> findByGatewayPaymentIdAndGatewayRefundId(String gatewayPaymentId, String gatewayRefundId);
}
