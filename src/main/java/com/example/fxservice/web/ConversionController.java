package com.example.fxservice.web;

import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
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
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exchangeRateDTO", bindingResult);
            return "redirect:/";
        }
        if(exchangeRateDTO.getSourceCurrency().equals(exchangeRateDTO.getTargetCurrency())){
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
}
