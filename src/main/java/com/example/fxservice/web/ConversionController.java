package com.example.fxservice.web;

import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.model.dtos.ExchangeRateDTO;
import com.example.fxservice.service.ConversionService;
import com.example.fxservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConversionController {
    private final ConversionService conversionService;
    private final UserService userService;

    public ConversionController(ConversionService conversionService, UserService userService) {
        this.conversionService = conversionService;
        this.userService = userService;
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
    @ModelAttribute("errorMessage")
    public String addErrorMessageValueToModel(){
        return "";
    }
    @GetMapping("/conversion")
    public String viewConversion() {
        return "conversion";
    }

    @GetMapping("/exchangeRate")
    public String getExchangeRate(@Valid ExchangeRateDTO exchangeRateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exchangeRateData", bindingResult);
            return "redirect:/";
        }
        if(exchangeRateDTO.getSourceCurrency().toUpperCase().equals(exchangeRateDTO.getTargetCurrency().toUpperCase())){
            if(this.conversionService.getSupportedCurrencies().contains(exchangeRateDTO.getSourceCurrency().toUpperCase())){
                redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRate", 1);
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            }else{
                redirectAttributes.addFlashAttribute("unsuccessfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
                redirectAttributes.addFlashAttribute("errorMessage", "There is no such currency");
            }
        }else{
            CurrencyLayerResponseDTO response = this.conversionService.getExchangeRate(exchangeRateDTO.getSourceCurrency(), exchangeRateDTO.getTargetCurrency());
            if(response.isSuccess()){
                redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
                String rateKey = exchangeRateDTO.getSourceCurrency().toUpperCase() + exchangeRateDTO.getTargetCurrency().toUpperCase();
                redirectAttributes.addFlashAttribute("exchangeRate", response.getQuotes().get(rateKey));
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
            }else{
                redirectAttributes.addFlashAttribute("unsuccessfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRateData", exchangeRateDTO);
                redirectAttributes.addFlashAttribute("errorMessage", response.getError().getInfo());
            }
        }
        return "redirect:/";
    }
    @PostMapping("/currency/conversion")
    public String currencyConversion(@Valid CurrencyConversionDTO currencyConversionDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("currencyConversionData", currencyConversionDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currencyConversionData", bindingResult);
            return "redirect:/conversion";
        }
        if(currencyConversionDTO.getSourceCurrency().toUpperCase().equals(currencyConversionDTO.getTargetCurrency().toUpperCase())){
            if(this.conversionService.getSupportedCurrencies().contains(currencyConversionDTO.getSourceCurrency().toUpperCase())){
                redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("exchangeRate", 1);
                redirectAttributes.addFlashAttribute("currencyConversionData", currencyConversionDTO);
                redirectAttributes.addFlashAttribute("savedConversion", this.conversionService.saveConversion(currencyConversionDTO, 1.0));
            }else{
                redirectAttributes.addFlashAttribute("unsuccessfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("currencyConversionData", currencyConversionDTO);
                redirectAttributes.addFlashAttribute("errorMessage", "There is no such currency");
            }
        }else{
            CurrencyLayerResponseDTO response = this.conversionService.getExchangeRate(currencyConversionDTO.getSourceCurrency(), currencyConversionDTO.getTargetCurrency());
            if(response.isSuccess()){
                redirectAttributes.addFlashAttribute("successfulGetExchangeRate", true);
                String rateKey = currencyConversionDTO.getSourceCurrency().toUpperCase() + currencyConversionDTO.getTargetCurrency().toUpperCase();
                Double exchangeRate = response.getQuotes().get(rateKey);
                redirectAttributes.addFlashAttribute("exchangeRate", exchangeRate);
                redirectAttributes.addFlashAttribute("currencyConversionData", currencyConversionDTO);
                redirectAttributes.addFlashAttribute("savedConversion", this.conversionService.saveConversion(currencyConversionDTO, exchangeRate));
            }else{
                redirectAttributes.addFlashAttribute("unsuccessfulGetExchangeRate", true);
                redirectAttributes.addFlashAttribute("currencyConversionData", currencyConversionDTO);
                redirectAttributes.addFlashAttribute("errorMessage", response.getError().getInfo());
            }
        }
        return "redirect:/conversion";
    }
}
