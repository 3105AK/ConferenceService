package com.brawlstars.ConferenceService.controllers;

import com.brawlstars.ConferenceService.models.Room;
import com.brawlstars.ConferenceService.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CreateRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @GetMapping("/create")
    public String createPage(){
        return "create";
    }
    @PostMapping("/create")
    public String createNext(@RequestParam(value = "timeStep", defaultValue = "180") int timeStep){
        Room room = new Room();
        Date today = new Date();
        room.setCreateTime(today.getTime());
        room.setTimeStep(timeStep);
        roomRepository.save(room);
        String id = room.getRoomId().toString();
        return "redirect:/room/"+id;
    }
}
