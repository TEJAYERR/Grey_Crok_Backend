package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.RegisterRequest;
import com.jashu.shopping_website.dto.RegisterResponse;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    UserRepo userRepo;

    @Autowired
    RegistrationService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public RegisterResponse registerNewUser(RegisterRequest registerRequest){

        User newUser = new User();

        try {
            if(userRepo.existsByEmail(registerRequest.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            if(userRepo.existsByMobileNumber(registerRequest.getMobileNumber())){
                throw new RuntimeException("Mobile Number Already Exists");
            }

            newUser.setUserName(registerRequest.getName());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
            newUser.setMobileNumber(registerRequest.getMobileNumber());

            User user = userRepo.save(newUser);
            return new RegisterResponse(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
