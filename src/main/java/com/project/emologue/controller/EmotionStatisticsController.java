package com.project.emologue.controller;

import com.project.emologue.model.dto.EmotionStatisticsDto;
import com.project.emologue.service.EmotionStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class EmotionStatisticsController {

    private final Logger logger = LoggerFactory.getLogger(EmotionStatisticsController.class);

    @Autowired
    private EmotionStatisticsService emotionStatisticsService;

    @GetMapping("/weekly/{userId}")
    public ResponseEntity<List<EmotionStatisticsDto>> getWeeklyStatistics(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(emotionStatisticsService.getWeeklyStatistics(userId));
    }

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<List<EmotionStatisticsDto>> getMonthlyStatistics(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(emotionStatisticsService.getMonthlyStatistics(userId));
    }
}
