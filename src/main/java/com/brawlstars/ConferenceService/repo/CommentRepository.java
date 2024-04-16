package com.brawlstars.ConferenceService.repo;
import com.brawlstars.ConferenceService.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
