package com.example.fxservice.web;

import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ConversionController {
    @ModelAttribute("currencyConversionData")
    public CurrencyConversionDTO addCurrencyConversionDataToModel() {
        return new CurrencyConversionDTO();
    }

    @GetMapping("/conversion")
    public String viewConversion() {
        return "conversion";
    }
}
