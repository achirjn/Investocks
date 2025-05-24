package com.investocks.services;

import java.util.Optional;

import com.investocks.entities.User;

public interface UserServices {

    Optional<User> validateLogin(String userName, String password);
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    void updateUserBalance(int userId, int amount);
}
