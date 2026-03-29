package com.jashu.shopping_website.dto;

public class LoginResponse {

    String userName;
    String email;
    String role;
    String JWT_TOKEN;

    public LoginResponse(){}

    public LoginResponse(String userName, String email, String role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJWT_TOKEN() {
        return JWT_TOKEN;
    }

    public void setJWT_TOKEN(String JWT_TOKEN) {
        this.JWT_TOKEN = JWT_TOKEN;
    }
}
