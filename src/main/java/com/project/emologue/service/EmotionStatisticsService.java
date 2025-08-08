package com.project.emologue.service;

import com.project.emologue.model.dto.DailyEmotionDto;
import com.project.emologue.model.dto.EmotionStatisticsDto;
import com.project.emologue.repository.EmotionStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmotionStatisticsService {

    private final Logger logger = LoggerFactory.getLogger(EmotionStatisticsService.class);

    @Autowired
    private EmotionStatisticsRepository statisticsRepository;

    public List<EmotionStatisticsDto> getWeeklyStatistics(Long userId) {
        return statisticsRepository.getWeeklyEmotionStatistics(userId);
    }

    public List<EmotionStatisticsDto> getMonthlyStatistics(Long userId) {
        return statisticsRepository.getMonthlyEmotionStatistics(userId);
    }

    public List<EmotionStatisticsDto> getJobsStatistics() {
        return statisticsRepository.getJobsEmotionStatistics();
    }

    public List<DailyEmotionDto> getEmotionTrendMonthly(Long userId) {
        return statisticsRepository.getEmotionTrendMonthly(userId);
    }

    public List<DailyEmotionDto> getEmotionTrendWeekly(Long userId) {
        return statisticsRepository.getEmotionTrendWeekly(userId);
    }
}
