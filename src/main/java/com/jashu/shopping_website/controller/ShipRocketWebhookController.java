package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.ShipRocketWebhookRequest;
import com.jashu.shopping_website.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ShipRocketWebhookController {

    OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/shiprocket/webhook")
    public ResponseEntity<?> shipRocketWebhook(@RequestBody ShipRocketWebhookRequest shipRocketWebhookRequest){
        return new ResponseEntity<>(orderService.updateOrderTrackingFromWebhook(shipRocketWebhookRequest), HttpStatus.OK);
    }
}
