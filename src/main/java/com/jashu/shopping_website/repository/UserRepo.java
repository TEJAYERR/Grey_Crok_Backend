package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//import java.lang.ScopedValue;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findUsersByUserName(String userName);

    User findUsersByUserNameAndPassword(String userName, String password);

    User findUsersByMobileNumberAndPassword(String mobileNumber, String password);

    User findUsersByMobileNumber(String mobileNumber);

    boolean existsUserByMobileNumberAndEmail(String mobileNumber, String email);

    boolean existsUserByMobileNumberOrEmail(String mobileNumber, String email);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    List<User> getUserByEmail(String email);

    User getByEmail(String email);
//    ScopedValue<Object> findByUserName(String userName);
}
