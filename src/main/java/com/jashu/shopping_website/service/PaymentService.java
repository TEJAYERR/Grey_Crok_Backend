package com.jashu.shopping_website.service;

import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.PaymentRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    public Payment updatePaymentDetails(UUID paymentId,
                                     String gatewayName,
                                     String gatewayOrderId,
                                     PaymentStatus paymentStatus,
                                     PaymentMethod paymentMethod
    ){

        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("payment not found!"));

        Order order = payment.getOrder();

        payment.setGatewayName(gatewayName);
        payment.setGatewayOrderId(gatewayOrderId);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentMethod(paymentMethod);

        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(paymentStatus);

        if(!paymentStatus.equals(PaymentStatus.PENDING)){
            order.setOrderStatus(OrderStatus.CONFIRMED);
        }

        return payment;
    }
}
