package com.example.fxservice.service.impl;

import com.example.fxservice.model.entities.UserEntity;
import com.example.fxservice.repository.UserRepository;
import com.example.fxservice.user.ForexUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.Optional;

public class ForexUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ForexUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(ForexUserDetailsService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private static UserDetails map(UserEntity user) {

        return new ForexUserDetails(
                user.getUsername(),
                user.getPassword()
        );
    }
}
