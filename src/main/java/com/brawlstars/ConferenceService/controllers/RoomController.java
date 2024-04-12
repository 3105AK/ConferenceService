package com.brawlstars.ConferenceService.controllers;


import com.brawlstars.ConferenceService.CommentSort;
import com.brawlstars.ConferenceService.models.Comment;
import com.brawlstars.ConferenceService.models.Room;
import com.brawlstars.ConferenceService.models.RoomJSON;
import com.brawlstars.ConferenceService.repo.CommentRepository;
import com.brawlstars.ConferenceService.repo.RoomRepository;
import com.brawlstars.ConferenceService.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
@Controller
public class RoomController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RoomRepository roomRepository;


    @GetMapping("/room")
    public String room(@PathVariable(value = "id") long roomId, Model model){
        if(!roomRepository.existsById(roomId)){
            return "redirect:/home";
        }
        /*
        Room room = roomRepository.findById(roomId).orElseThrow();
        roomRepository.save(room);

        Optional<Comment> comments = postRepository.findById(id);
        ArrayList<Comment> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);*/
        return "room";
    }
    @ResponseBody
    @GetMapping("/room/{id}")
    public RoomJSON roomEdit(@PathVariable(value = "id") long roomId, @RequestParam(value = "text") String text, @RequestParam(value = "userId") long userId){
        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();

        Comment comment = new Comment();
        comment.setText(text);
        comment.setRoomId(roomId);
        comment.setUserId(userId);
        comment.setCreatedTime(today.getTime());
        comment.setLastTime((comment.getCreatedTime() + room.getTimeStep() - today.getTime())/1000);//в секундах

        Iterable<Comment> allComments = commentRepository.findAll();
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep() >= today.getTime()){
                commentRepository.delete(com);
                continue;
            }
            com.setLastTime((com.getCreatedTime() + room.getTimeStep() - today.getTime())/1000);
            comments.add(com);
        }
        comments.add(comment);
        commentRepository.save(comment);

        comments = CommentSort.quickSort(comments);

        return new RoomJSON(comments);
    }
    @ResponseBody
    @PostMapping("/room/{id}")
    public RoomJSON roomUpdate(@PathVariable(value = "id") long roomId){
        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();

        Iterable<Comment> allComments = commentRepository.findAll();
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep() >= today.getTime()){
                commentRepository.delete(com);
                continue;
            }
            com.setLastTime((com.getCreatedTime() + room.getTimeStep() - today.getTime())/1000);
            comments.add(com);
        }

        comments = CommentSort.quickSort(comments);

        return new RoomJSON(comments);
    }
}
