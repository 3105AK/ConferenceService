package com.brawlstars.ConferenceService.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnterController {
    @GetMapping("/enter")
    public String enterPage(){
        return "enter";
    }
    @PostMapping("/enter")
    public String getEnterData(@RequestParam( value = "login") String login, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email){
        //обработка данных
        return "redirect:/home";
    }
}
