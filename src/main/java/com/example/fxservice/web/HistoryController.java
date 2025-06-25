package com.example.fxservice.web;

import com.example.fxservice.model.dtos.ConversionHistoryDTO;
import com.example.fxservice.service.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HistoryController {
    private final ConversionService conversionService;

    public HistoryController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @ModelAttribute("conversionsList")
    public List<ConversionHistoryDTO> addConversionsListToModel(){
        return new ArrayList<>();
    }
    @ModelAttribute("successfulSearch")
    public boolean addSuccessfulSearchValueToModel(){
        return true;
    }
    @GetMapping("/history")
    public String viewHistory(Model model){
        model.addAttribute("conversionsList", this.conversionService.getAllConversionsForUser());
        return "history";
    }
    @GetMapping("/history/unsuccessful/search")
    public String viewUnsuccessfulSearch(Model model){
        model.addAttribute("successfulSearch", false);
        model.addAttribute("conversionsList", new ArrayList<>());
        return "/history";
    }
    @GetMapping("/history/search")
    public String searchHistory(@RequestParam("search") String searchWord, Model model, RedirectAttributes redirectAttributes){
        if(searchWord == null || searchWord.isBlank()){
            return "redirect:/history/unsuccessful/search";
        }
        List<ConversionHistoryDTO> conversionHistoryDTOS = this.conversionService.searchForConversions(searchWord);
        if(conversionHistoryDTOS.isEmpty()){
            return "redirect:/history/unsuccessful/search";
        }
        model.addAttribute("conversionsList", conversionHistoryDTOS);
        return "/history";
    }
}
