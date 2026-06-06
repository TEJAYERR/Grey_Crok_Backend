package com.jashu.shopping_website.controller;

import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/profile")
    public String profile(@RequestHeader("Authorization") String authorization){

        if(authorization == null || !authorization.startsWith("Bearer ")){
            return "Invalid token";
        }

        String token = authorization.substring(7);
        String[] data = TokenUtil.decodeToken(token);

        if(data.length < 2){
            throw new IllegalArgumentException("Misformed Token");
        }

        String email = data[0];
        String role = data[1];

        if(!role.equalsIgnoreCase("user")){
            return "unauth";
        }

//        try{
//
//        }
        return "";
    }


}
