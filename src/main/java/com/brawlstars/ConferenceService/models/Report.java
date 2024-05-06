package com.brawlstars.ConferenceService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String text;
    Long createTime;
    String currentTime;
}
