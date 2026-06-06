package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.LoginRequest;
import com.jashu.shopping_website.dto.LoginResponse;
import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.UserRepo;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    UserRepo loginRepo;

    AuthenticationManager authenticationManager;

    JWTService jwtService;

    @Autowired
    LoginService(UserRepo loginRepo, AuthenticationManager authenticationManager, JWTService jwtService){

        this.loginRepo = loginRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse validateCredentials(LoginRequest loginRequest){

        System.out.println("Entering the authentication");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMobileNumber(), loginRequest.getPassword()));

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();

        System.out.println("User = " + user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserName(user.getUserName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());

        String token = jwtService.generateTokenWithMobileNumber(user.getMobileNumber());
        loginResponse.setJWT_TOKEN(token);

        return loginResponse;
    }
}
