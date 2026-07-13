package com.jashu.shopping_website.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shipmentId;
    private String shipmentProvider;
    private String awbCode;
    private String courierName;
    private String trackingStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
