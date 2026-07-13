package com.jashu.shopping_website.dto;

import com.jashu.shopping_website.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    String userName;
    String email;
    Role role;
    String JWT_TOKEN;

    public LoginResponse(){}
}
