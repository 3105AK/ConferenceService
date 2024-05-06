package com.brawlstars.ConferenceService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class NumberCount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
