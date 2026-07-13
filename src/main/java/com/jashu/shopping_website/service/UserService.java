package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.UserResponse;
import com.jashu.shopping_website.entities.Address;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public UserResponse profile(UUID userId){

         User user = userRepo.findById(userId)
                 .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

         return new UserResponse(user);
    }

    public Address setAddress(UUID userId, Address address) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        user.setAddress(address);
        userRepo.save(user);

        return user.getAddress();
    }
}
