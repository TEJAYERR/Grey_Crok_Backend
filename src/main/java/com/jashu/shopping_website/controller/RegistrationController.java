package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.RegisterRequest;
import com.jashu.shopping_website.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequest registerRequest){

        try {
            return new ResponseEntity<>(registrationService.registerNewUser(registerRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
