package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.entities.Address;
import com.jashu.shopping_website.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserPrinciple userPrinciple){

        return new ResponseEntity<>(userService.profile(userPrinciple.getUser().getUserId()), HttpStatus.OK);

    }

    @PostMapping("/address")
    public ResponseEntity<?> updateAddress(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @RequestBody Address address){

        return ResponseEntity.ok(userService.setAddress(userPrinciple.getUser().getUserId(), address));
    }
}
