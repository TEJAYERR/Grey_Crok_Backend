package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.RegisterRequest;
import com.jashu.shopping_website.dto.RegisterResponse;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.exception.DuplicateEntryException;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    UserRepo userRepo;

    @Autowired
    RegistrationService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerNewUser(RegisterRequest registerRequest){

        if(userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEntryException("Email already exists");
        }

        if(userRepo.existsByMobileNumber(registerRequest.getMobileNumber())){
            throw new DuplicateEntryException("Mobile Number Already Exists");
        }

        User newUser = new User();

        newUser.setUserName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setMobileNumber(registerRequest.getMobileNumber());

        newUser = userRepo.save(newUser);

        return new RegisterResponse(newUser);
    }
}
