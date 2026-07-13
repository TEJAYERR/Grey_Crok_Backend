package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {

    private String name;
    private String email;
    private String mobileNumber;

    public RegisterResponse(){}

    public RegisterResponse(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public RegisterResponse(User user){
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.mobileNumber = user.getMobileNumber();
    }

}
