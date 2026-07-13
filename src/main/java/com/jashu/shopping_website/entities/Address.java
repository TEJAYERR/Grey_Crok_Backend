package com.jashu.shopping_website.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Address {

    @Column(name = "door_number")
    String doorNumber;
    String street;
    String city;
    String district;
    String state;
    String pincode;
    String country;

    public Address() {
    }
}
