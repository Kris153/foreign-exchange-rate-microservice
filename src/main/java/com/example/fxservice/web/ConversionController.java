package com.example.fxservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConversionController {
    @GetMapping("/conversion")
    public String viewConversion(){
        return "conversion";
    }
}
