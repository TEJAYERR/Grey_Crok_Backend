package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.LoginRequest;
import com.jashu.shopping_website.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    final private LoginService loginService;

    @Autowired
    LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> validate(@RequestBody LoginRequest request){
        try {
            System.out.println("hello from login controller");
            return new ResponseEntity<>(loginService.validateCredentials(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
