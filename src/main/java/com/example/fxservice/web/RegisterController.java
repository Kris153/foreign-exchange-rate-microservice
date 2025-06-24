package com.example.fxservice.web;

import com.example.fxservice.model.dtos.UserRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {
    @ModelAttribute("registerData")
    public UserRegisterDTO addRegisterDTOToModel(){
        return new UserRegisterDTO();
    }
    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }
}
