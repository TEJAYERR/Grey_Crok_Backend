package com.jashu.shopping_website.service;

import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public String profile(String email){
        return "";
    }
}
