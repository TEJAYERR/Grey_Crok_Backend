package com.jashu.shopping_website.entities;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int addressId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    String doorNumber;
    String street;
    String city;
    String district;
    String state;
    String pincode;
    String country;

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", doorNumber='" + doorNumber + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
