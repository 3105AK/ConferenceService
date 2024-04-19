package com.brawlstars.ConferenceService.controllers;


import com.brawlstars.ConferenceService.CommentSort;
import com.brawlstars.ConferenceService.models.Comment;
import com.brawlstars.ConferenceService.models.NumberCount;
import com.brawlstars.ConferenceService.models.Room;
import com.brawlstars.ConferenceService.models.RoomJSON;
import com.brawlstars.ConferenceService.repo.CommentRepository;
import com.brawlstars.ConferenceService.repo.NumberCountRepository;
import com.brawlstars.ConferenceService.repo.RoomRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private CommentRepository commentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NumberCountRepository numberCountRepository;


    @GetMapping("/room/{id}")
    public String room(@PathVariable(value = "id") long roomId,
                       @CookieValue(value = "id", defaultValue = "-1") Long userId,
                       @CookieValue(value = "own", defaultValue = "-1") Long own,
                       @RequestParam(value = "password", defaultValue = "") String password1,
                       @CookieValue(value = "password", defaultValue = "") String password2,
                       HttpServletResponse response,
                       Model model){

        if(userId==-1){
            NumberCount num = numberCountRepository.save(new NumberCount());
            Cookie cookie1 = new Cookie("id", num.getId().toString());
            cookie1.setPath("/");
            response.addCookie(cookie1);
        }
        if(!password1.equals("")){
            Cookie cookie2 = new Cookie("password", password1);
            cookie2.setPath("/");
            response.addCookie(cookie2);
        }



        if(!roomRepository.existsById(roomId)){
            return "redirect:/";
        }
        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();
        Iterable<Comment> allComments = new ArrayList<>();
        try{
        allComments = commentRepository.findAll();}
        catch(Exception ex){
        }
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes()>= today.getTime()){
                commentRepository.delete(com);
                continue;
            }
            if(room.getTimeStep()!=0) com.setLastTime((com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes() - today.getTime())/1000);
            else com.setLastTime(0);
            comments.add(com);
        }

        comments = CommentSort.quickSort(comments);
        model.addAttribute("comments", comments);
        model.addAttribute("roomId", roomId);
        model.addAttribute("name", room.getName());
        /*
        Room room = roomRepository.findById(roomId).orElseThrow();
        roomRepository.save(room);

        Optional<Comment> comments = postRepository.findById(id);
        ArrayList<Comment> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);*/
        if((roomId==own||room.getPassword().equals(password1)||room.getPassword().equals(password2))&&!room.getPassword().equals("")) return "roomAdmin";
        return "room";
    }
    @ResponseBody
    @PostMapping("/room/{id}")
    public RoomJSON roomUpdate(@PathVariable(value = "id") long roomId,
                               @RequestParam(value = "likeId", defaultValue = "-1") long like,
                               @CookieValue(value = "id", defaultValue = "-1") long userId){
        if(!roomRepository.existsById(roomId)){
            return new RoomJSON(roomId, "", new ArrayList<Comment>());
        }
        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();


        if(like!=-1&&userId!=-1){
            Boolean check = true;
            try{
                Optional<Comment> likedCommentOptional = commentRepository.findById(like);
                if(likedCommentOptional.isPresent()){
                    Comment likedComment = likedCommentOptional.get();
                    ArrayList<Long> likedComments = likedComment.getLikedBy();
                    for (Long com: likedComments){
                        if(com==userId){
                            check = false;
                            break;
                        }
                    }
                    if(check){
                        likedComment.setLikes(likedComment.getLikes()+1);
                        likedComments.add(userId);
                        likedComment.setLikedBy(likedComments);
                        likedComment.setLastTime(likedComment.getLastTime()+room.getTimeStep()*1000);
                        commentRepository.save(likedComment);
                    }
                }
            }
            catch(Exception ex){
            }

        }



        Iterable<Comment> allComments = new ArrayList<>();
        try{
            allComments = commentRepository.findAll();}
        catch(Exception ex){
        }
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes() >= today.getTime()){
                commentRepository.delete(com);
                continue;
            }
            if(room.getTimeStep()!=0) com.setLastTime((com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes() - today.getTime())/1000);
            else com.setLastTime(0);
            comments.add(com);
        }

        comments = CommentSort.quickSort(comments);

        return new RoomJSON(roomId, room.getName(), comments);
    }
    @PostMapping("/room/{id}/delete")
    public String roomDelete(@PathVariable(value = "id") long roomId,
                             @RequestParam(value = "commentId") long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/room/{id}";
    }
    @GetMapping("/room/{id}/add")
    public String roomAdd(@PathVariable(value = "id") long roomId,
                             @CookieValue(value = "id", defaultValue = "-1") long userId,
                             @RequestParam(value = "text") String text){

        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();

        Comment comment = new Comment();
        comment.setText(text);
        comment.setRoomId(roomId);
        comment.setUserId(userId);
        comment.setCreatedTime(today.getTime());
        comment.setLastTime((comment.getCreatedTime() + room.getTimeStep() - today.getTime())/1000);//в секундах


        commentRepository.save(comment);


        return "redirect:/room/{id}";
    }
    @GetMapping("/room/{id}/deleteRoom")
    public String delRoom(@PathVariable(value = "id") Long roomId,
                          @CookieValue(value = "own", defaultValue = "-1") Long own){
        if(!roomId.equals(own)) return "redirect:/room/{id}";
        roomRepository.deleteById(roomId);
        return "redirect:/";
    }

}
