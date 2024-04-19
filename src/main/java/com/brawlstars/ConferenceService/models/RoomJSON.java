package com.brawlstars.ConferenceService.models;


import java.util.ArrayList;

public record RoomJSON(Long roomId, String roomName, ArrayList<Comment> comments) {
}
