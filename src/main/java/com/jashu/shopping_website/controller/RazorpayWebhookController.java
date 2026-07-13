package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.service.RazorpayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webhooks/razorpay")
public class RazorpayWebhookController {

    private final RazorpayService razorpayService;

    public RazorpayWebhookController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhook(@RequestBody String payload,
                                                  @RequestHeader("X-Razorpay-Signature") String signature) throws Exception {

        razorpayService.handleWebhook(payload, signature);
        return ResponseEntity
                .ok()
                .build();
    }
}
