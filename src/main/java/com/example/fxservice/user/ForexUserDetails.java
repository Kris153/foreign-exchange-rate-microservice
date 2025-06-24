package com.example.fxservice.user;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class ForexUserDetails extends User {
    public ForexUserDetails(String username, String password) {
        super(username, password, Collections.emptyList());
    }
}
