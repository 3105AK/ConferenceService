package com.brawlstars.ConferenceService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    private Integer timeStep;
    private Long createTime;
    private String password;
    //private String code;
    //private Long userId;
    //private ArrayList<Integer> comments;

}
