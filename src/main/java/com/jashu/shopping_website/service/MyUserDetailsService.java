package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.UserPrinciple;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.UserRepo;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    UserRepo userRepo;

    @Autowired
    public MyUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {

        System.out.println("Mobile Number = " + mobileNumber);

        User user = userRepo.findUsersByMobileNumber(mobileNumber);

        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }

        System.out.println("DB User: " + user.getMobileNumber());
        System.out.println("DB Password: " + user.getPassword());

        return new UserPrinciple(user);
    }
}
