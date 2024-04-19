package com.brawlstars.ConferenceService.controllers;
import com.brawlstars.ConferenceService.models.Report;
import com.brawlstars.ConferenceService.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    ReportRepository reportRepository;
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @PostMapping("/")
    public String report(@RequestParam(value = "text", defaultValue = "") String text){
        if(text .equals( "" )) return "home";
        Date date = new Date();
        Report report = new Report();
        report.setText(text);
        report.setCreateTime(date.getTime());
        reportRepository.save(report);
        return "home";
    }
}
