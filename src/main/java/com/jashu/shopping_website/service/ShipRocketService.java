package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.ShipmentOrderRequest;
import com.jashu.shopping_website.dto.ShipmentOrderResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShipRocketService {

    String token;

    public String authenticate(String email, String password){

        String url = "https://apiv2.shiprocket.in/v1/external/auth/login";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("passwprd", password);

        HttpEntity<Map<String, String>> httpRequest = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpRequest,
                Map.class
        );

        Map responseBody = response.getBody();

        if(responseBody != null){
            return "something went wrong";
        }

        this.token = responseBody.get("token").toString();

        return this.token;
    }

    public ShipmentOrderResponse createShipment(String token, ShipmentOrderRequest shipmentOrderRequest){
        if(!token.equals(this.token)){
            throw new RuntimeException("Token mismatch");
        }

        System.out.println("Shipment Created Successfully");
        ShipmentOrderResponse shipmentOrderResponse = new ShipmentOrderResponse();

        shipmentOrderResponse.setShipmentId(shipmentOrderRequest.getOrderId() + "soi");
        shipmentOrderResponse.setOrderId(shipmentOrderRequest.getOrderId());
        shipmentOrderResponse.setAwbCode("awb-"+shipmentOrderRequest.getOrderId());
        shipmentOrderResponse.setTrackingStatus("new");
        shipmentOrderResponse.setCourierName("cn-" + shipmentOrderRequest.getOrderId());

        return shipmentOrderResponse;
    }
}
