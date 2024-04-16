package com.brawlstars.ConferenceService.repo;
import com.brawlstars.ConferenceService.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>{}