package com.jashu.shopping_website.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

    String name;
    String email;
    String mobileNumber;
    String password;
}
