package com.example.fxservice.service.impl;

import com.example.fxservice.model.dtos.UserRegisterDTO;
import com.example.fxservice.model.entities.UserEntity;
import com.example.fxservice.repository.UserRepository;
import com.example.fxservice.service.UserService;
import com.example.fxservice.service.exception.ObjectNotFoundException;
import com.example.fxservice.user.ForexUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegisterDTO registerData) {
        UserEntity userToAdd = new UserEntity();
        userToAdd.setUsername(registerData.getUsername());
        userToAdd.setPassword(this.passwordEncoder.encode(registerData.getPassword()));
        userToAdd.setConversions(new ArrayList<>());
        this.userRepository.saveAndFlush(userToAdd);
    }

    @Override
    public boolean confirmPassword(UserRegisterDTO registerData) {
        return registerData.getPassword().equals(registerData.getConfirmPassword());
    }

    @Override
    public boolean doUsernameExists(UserRegisterDTO registerData) {
        return this.userRepository.existsUserEntityByUsername(registerData.getUsername());
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof ForexUserDetails forexUserDetails) {
            Optional<UserEntity> byUsername = this.userRepository.findByUsername(forexUserDetails.getUsername());
            if(byUsername.isEmpty()){
                throw new ObjectNotFoundException();
            }
            return byUsername.get();
        }
        return null;
    }

}
