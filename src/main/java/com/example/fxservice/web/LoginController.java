package com.example.fxservice.web;

import com.example.fxservice.model.dtos.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {
    @ModelAttribute("loginData")
    private UserLoginDTO addUserLoginDTOToModel(){
        return new UserLoginDTO();
    }
    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }
}
