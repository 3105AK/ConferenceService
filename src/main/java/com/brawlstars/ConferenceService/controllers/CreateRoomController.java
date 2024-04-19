package com.brawlstars.ConferenceService.controllers;


import com.brawlstars.ConferenceService.models.Room;
import com.brawlstars.ConferenceService.models.NumberCount;
import com.brawlstars.ConferenceService.repo.NumberCountRepository;
import com.brawlstars.ConferenceService.repo.RoomRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CreateRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NumberCountRepository numberCountRepository;
    @GetMapping("/create")
    public String createPage(@CookieValue(value = "id", defaultValue = "-1") String userId, HttpServletResponse response){
        if(userId.equals("-1")){
            NumberCount num = numberCountRepository.save(new NumberCount());
            Cookie cookie = new Cookie("id", num.getId().toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "create";
    }
    @PostMapping("/create")
    public String createNext(@RequestParam(value = "timeStep", defaultValue = "180") int timeStep,
                             @RequestParam(value = "password", defaultValue = "") String password,
                             @RequestParam(value = "name", defaultValue = "") String name,
                             @CookieValue(value = "id", defaultValue = "-1") String userId,
                             @CookieValue(value = "own", defaultValue = "-1") String own,
                             HttpServletResponse response){
        if(userId.equals("-1")) return "redirect:/";
        Room room = new Room();
        Date today = new Date();
        room.setCreateTime(today.getTime());
        room.setTimeStep(timeStep);
        room.setPassword(password);
        room.setName(name);
        room = roomRepository.save(room);
        String id = room.getRoomId().toString();
        if(!own.equals("-1")) roomRepository.deleteById(Long.valueOf(own));
        Cookie cookie = new Cookie("own", id);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/room/"+id;
    }
}
