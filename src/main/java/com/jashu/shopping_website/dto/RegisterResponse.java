package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.User;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
