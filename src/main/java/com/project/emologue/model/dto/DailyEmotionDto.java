package com.project.emologue.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Map;

public class DailyEmotionDto {
    @Schema(description = "다이어리 날짜", example = "2025-08-08")
    private LocalDate date;
    @Schema(description = "감정 타입별 카운트", example = "{\"POSITIVE\":1, \"NEGATIVE\":1}")
    private Map<String, Long> emotionCounts;

    public DailyEmotionDto(LocalDate date, Map<String, Long> emotionCounts) {
        this.date = date;
        this.emotionCounts = emotionCounts;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Long> getEmotionCounts() {
        return emotionCounts;
    }

    public void setEmotionCounts(Map<String, Long> emotionCounts) {
        this.emotionCounts = emotionCounts;
    }
}
