package com.example.fxservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {
    @GetMapping("/history")
    public String viewHistory(){
        return "history";
    }
}
