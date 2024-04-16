package com.brawlstars.ConferenceService.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Entity;

import java.util.ArrayList;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private long roomId;
    private int likes = 0;
    private long createdTime;
    private long LastTime;
    private long userId;
    private ArrayList<Long> likedBy;
}
