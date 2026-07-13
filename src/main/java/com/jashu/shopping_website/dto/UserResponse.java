package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Address;
import com.jashu.shopping_website.entities.Role;

import com.jashu.shopping_website.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserResponse {

    UUID userId;
    String userName;
    String email;
    String mobileNumber;
    Address address;
    Role role;

    public UserResponse(User user){
        this.email = user.getEmail();
        this.mobileNumber = user.getMobileNumber();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.address = user.getAddress();
        this.role = user.getRole();
    }
}
