package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ShipRocketWebhookRequest {

    private UUID shipmentId;
    private String awbCode;
    private String courierName;
    private String courierStatus;

}
