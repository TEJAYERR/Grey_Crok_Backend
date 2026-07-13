package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.LoginRequest;
import com.jashu.shopping_website.dto.LoginResponse;
import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public LoginService(AuthenticationManager authenticationManager, JWTService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse validateCredentials(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMobileNumber(), loginRequest.getPassword()));

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();

        String token = jwtService.generateTokenWithMobileNumber(user.getMobileNumber());

        return new LoginResponse(user.getUserName(), user.getEmail(), user.getRole(), token);
    }
}
