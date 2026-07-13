package com.jashu.shopping_website.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID userId;
    String userName;
    @Column(nullable = false, unique = true)
    String email;
    @Column(unique = true, nullable = false)
    String mobileNumber;
    String password;

    @Enumerated(EnumType.STRING)
    Role role = Role.USER;

    @Embedded
    Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orders;
}
