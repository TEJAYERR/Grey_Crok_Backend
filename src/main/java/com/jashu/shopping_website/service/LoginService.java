package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.LoginRequest;
import com.jashu.shopping_website.dto.LoginResponse;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.UserRepo;
import com.jashu.shopping_website.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    UserRepo loginRepo;

    @Autowired
    LoginService(UserRepo loginRepo){
        this.loginRepo = loginRepo;
    }

    public LoginResponse validateCredentials(LoginRequest loginRequest){
        User user = loginRepo.findUsersByMobileNumber(loginRequest.getMobileNumber());

        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("password incorrect");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserName(user.getUserName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());
        String token = TokenUtil.encodeToken(loginResponse.getEmail(), loginResponse.getRole());
        loginResponse.setJWT_TOKEN(token);

        return loginResponse;
    }
}
