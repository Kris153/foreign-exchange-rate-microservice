package com.example.fxservice.service;

import com.example.fxservice.model.dtos.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO registerData);
    boolean confirmPassword(UserRegisterDTO registerData);

    boolean doUsernameExists(UserRegisterDTO registerData);
}
