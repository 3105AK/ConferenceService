package com.brawlstars.ConferenceService.repo;

import com.brawlstars.ConferenceService.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
