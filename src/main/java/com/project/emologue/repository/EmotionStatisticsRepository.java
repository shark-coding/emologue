package com.project.emologue.repository;

import com.project.emologue.model.dto.DailyEmotionDto;
import com.project.emologue.model.dto.EmotionStatisticsDto;
import java.util.List;

public interface EmotionStatisticsRepository {
    List<EmotionStatisticsDto> getWeeklyEmotionStatistics(Long userId);
    List<EmotionStatisticsDto> getMonthlyEmotionStatistics(Long userID);
    List<EmotionStatisticsDto> getJobsEmotionStatistics();
    List<DailyEmotionDto> getEmotionTrendMonthly(Long userId);
    List<DailyEmotionDto> getEmotionTrendWeekly(Long userId);
}
