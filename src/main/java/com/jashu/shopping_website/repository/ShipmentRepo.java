package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentRepo extends JpaRepository<Shipment, UUID> {
}
