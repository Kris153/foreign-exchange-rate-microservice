package com.example.fxservice.web;

import com.example.fxservice.model.dtos.ConversionHistoryDTO;
import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HistoryController {
    @ModelAttribute("conversionsList")
    public List<ConversionHistoryDTO> addConversionsListToModel(){
        return new ArrayList<>();
    }
    @GetMapping("/history")
    public String viewHistory(){
        return "history";
    }
}
