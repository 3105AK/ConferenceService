package com.brawlstars.ConferenceService.controllers;

import com.brawlstars.ConferenceService.CommentSort;
import com.brawlstars.ConferenceService.models.Comment;
import com.brawlstars.ConferenceService.models.Report;
import com.brawlstars.ConferenceService.models.Room;
import com.brawlstars.ConferenceService.models.RoomJSON;
import com.brawlstars.ConferenceService.repo.CommentRepository;
import com.brawlstars.ConferenceService.repo.ReportRepository;
import com.brawlstars.ConferenceService.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    CommentRepository commentRepository;
    @GetMapping("/admin")
    public String admin(Model model){
        List<Room> rooms = new ArrayList();
        try{
            rooms = roomRepository.findAll();
        } catch (Exception e) {
        }
        model.addAttribute("rooms", rooms);
        return "admin";
    }
    @GetMapping("/admin/reports")
    public String adminReports(Model model){
        Date date = new Date();
        List<Report> reports = new ArrayList();
        try{
            reports = reportRepository.findAll();
        } catch (Exception e) {
        }
        for(Report report: reports){
            if(date.getTime()-report.getCreateTime()<120*60*1000) report.setCurrentTime((date.getTime()- report.getCreateTime())/(1000*60)+" mins ago");
            else if(date.getTime()-report.getCreateTime()<1000*60*60*48) report.setCurrentTime((date.getTime()- report.getCreateTime())/(1000*60*60)+" hours ago");
            else report.setCurrentTime((date.getTime()- report.getCreateTime())/(1000*60*60*24)+" days ago");
        }
        model.addAttribute("reports", reports);
        return "reports";
    }
    @PostMapping("/admin/reports")
    public String delReport(@RequestParam(value = "reportId", defaultValue = "-1") Long id){
        if(id!=-1) reportRepository.deleteById(id);
        return "redirect:/admin/reports";
    }
    @GetMapping("admin/{id}")
    public String adminRooms(@PathVariable(value = "id") Long roomId, Model model){
        if(!roomRepository.existsById(roomId)){
            return "redirect:/admin";
        }
        Room room = roomRepository.findById(roomId).orElseThrow();
        Date today = new Date();

        Iterable<Comment> allComments = commentRepository.findAll();
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes()>= today.getTime() && room.getTimeStep()!=0){
                commentRepository.delete(com);
                continue;
            }
            if(room.getTimeStep()!=0) com.setLastTime((com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes() - today.getTime())/1000);
            else com.setLastTime(0);
            comments.add(com);
        }

        comments = CommentSort.quickSort(comments);
        model.addAttribute("comments", comments);
        return "roomAdmin";
    }
    @ResponseBody
    @PostMapping("/admin/{id}")
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
                    commentRepository.save(likedComment);
                }
            }
        }



        Iterable<Comment> allComments = commentRepository.findAll();
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment com: allComments) {
            if(com.getRoomId()!=roomId) continue;
            if(com.getCreatedTime() + room.getTimeStep()*1000 + room.getTimeStep()*1000*com.getLikes()>= today.getTime() && room.getTimeStep()!=0){
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
    @PostMapping("/admin/{id}/delete")
    public String roomDelete(@PathVariable(value = "id") long roomId,
                             @RequestParam(value = "commentId") long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/admin/{id}";
    }
    @GetMapping("/admin/{id}/deleteRoom")
    public String delRoom(@PathVariable(value = "id") Long roomId){
        roomRepository.deleteById(roomId);
        List<Comment> comments = new ArrayList<>();
        try{
            comments = commentRepository.findAll();
        }
        catch (Exception ex){}

        for(Comment com: comments){
            if(com.getRoomId()==roomId) commentRepository.delete(com);
        }
        return "redirect:/admin";
    }
}
