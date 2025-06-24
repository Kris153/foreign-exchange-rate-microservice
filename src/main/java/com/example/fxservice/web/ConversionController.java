package com.example.fxservice.web;

import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.ExchangeRateDTO;
import com.example.fxservice.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @ModelAttribute("currencyConversionData")
    public CurrencyConversionDTO addCurrencyConversionDataToModel() {
        return new CurrencyConversionDTO();
    }
    @ModelAttribute("successfulGetExchangeRate")
    public boolean addSuccessfulGetExchangeRateValueToModel(){
        return false;
    }
    @ModelAttribute("unsuccessfulGetExchangeRate")
    public boolean addUnsuccessfulGetExchangeRateValueToModel(){
        return false;
    }
    @ModelAttribute("exchangeRate")
    public Double addExchangeRateValueToModel(){
        return null;
    }
    @GetMapping("/conversion")
    public String viewConversion() {
        return "conversion";
    }

    @GetMapping("/exchangeRate")
    public String getExchangeRate(@Valid ExchangeRateDTO exchangeRateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exchangeRateDTO", bindingResult);
            return "redirect:/";
        }
        if(exchangeRateDTO.getSourceCurrency().equals(exchangeRateDTO.getTargetCurrency())){
            redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
            redirectAttributes.addFlashAttribute("exchangeRate", 1);
            redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
        }else{
            Optional<Double> exchangeRate = this.conversionService.getExchangeRate(exchangeRateDTO.getSourceCurrency(), exchangeRateDTO.getTargetCurrency());
            if(exchangeRate.isPresent()){
                redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRate", exchangeRate.get());
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            }else{
                redirectAttributes.addFlashAttribute("unsuccessfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            }
        }
        return "redirect:/";
    }
}
