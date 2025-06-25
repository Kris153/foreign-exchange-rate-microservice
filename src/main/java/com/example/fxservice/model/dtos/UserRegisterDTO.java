package com.example.fxservice.model.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterDTO {
    @NotBlank(message = "This field should not be empty")
    private String username;
    @NotBlank(message = "This field should not be empty")
    private String password;
    @NotBlank(message = "This field should not be empty")
    private String confirmPassword;

    public UserRegisterDTO(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
