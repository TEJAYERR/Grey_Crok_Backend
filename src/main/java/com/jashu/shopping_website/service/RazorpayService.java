package com.jashu.shopping_website.service;

import com.jashu.shopping_website.entities.*;
import com.jashu.shopping_website.exception.InvalidSignatureException;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.OrderRepo;
import com.jashu.shopping_website.repository.PaymentRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class RazorpayService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    @Value("${spring.app.razorpay.key}")
    private String key;
    @Value("${spring.app.razorpay.secreatkey}")
    private String secretKey;
    @Value("${spring.app.razorpay.webhooksecret}")
    private String webhookSecret;

    public RazorpayService(PaymentRepo paymentRepo, OrderRepo orderRepo, ProductRepo productRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }


    public com.razorpay.Order createRazorpayOrder(Order newOrder) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key, secretKey);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", newOrder.getTotalAmount()
                .multiply(BigDecimal.valueOf(100))
                .intValueExact()
        );
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", UUID.randomUUID().toString());

        return razorpayClient.orders.create(jsonObject);
    }

    public String generateSignature(String data) throws Exception {

        return hmacSha256(data, secretKey);
    }

    private static String hmacSha256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey =
                new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Hex.encodeHexString(rawHmac);
    }

    public Refund refund(String paymentId) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key, secretKey);
        return razorpayClient.payments.refund(paymentId);
    }

    @Transactional
    public void handleWebhook(String payload, String signature) throws Exception {

        String expectedSignature = hmacSha256(payload, this.webhookSecret);

        if(!Objects.equals(signature, expectedSignature)){
            throw new InvalidSignatureException("signature is not valid");
        }

        JSONObject jsonObject = new JSONObject(payload);
        String event = jsonObject.getString("event");

        switch (event){

            case "refund.processed":
                handleRefundProcessed(jsonObject);
                break;

//            case "refund.failed":
//                handleRefundFailed(json);
//                break;
//
//            case "payment.captured":
//                handlePaymentCaptured(json);
//                break;
//
//            case "payment.failed":
//                handlePaymentFailed(json);
//                break;

            default:
                break;
        }

    }

    @Transactional
    protected void handleRefundProcessed(JSONObject jsonObject){

        JSONObject refund = jsonObject
                .getJSONObject("payload")
                .getJSONObject("refund")
                .getJSONObject("entity");

        String gatewayRefundId = refund.getString("id");
        String gatewayPaymentId = refund.getString("payment_id");

        Payment payment = paymentRepo.findByGatewayPaymentIdAndGatewayRefundId(
                gatewayPaymentId,
                gatewayRefundId
        ).orElseThrow(() -> new ResourceNotFoundException("payment not found !"));

        Order order = payment.getOrder();

        if(payment.getPaymentStatus().equals(PaymentStatus.REFUNDED)){
            return;
        }

        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        payment.setRefundedTime(LocalDateTime.now());

        order.setPaymentStatus(PaymentStatus.REFUNDED);
        order.setOrderStatus(OrderStatus.CANCELLED);
    }

}
