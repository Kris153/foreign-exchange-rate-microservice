package com.example.fxservice.web;

import com.example.fxservice.model.dtos.ExchangeRateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @ModelAttribute("exchangeRateData")
    public ExchangeRateDTO addExchangeRateDataValueToModel(){
        return new ExchangeRateDTO();
    }
    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }
}
