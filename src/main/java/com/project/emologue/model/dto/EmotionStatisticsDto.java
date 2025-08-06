package com.project.emologue.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmotionStatisticsDto {
    @Schema(description = "감정 타입", example = "POSITIVE")
    private String emotionType;
    @Schema(description = "갯수", example = "3")
    private long count;
    @Schema(description = "비율", example = "0.5")
    private double ratio;
    @Schema(description = "직업", example = "backend")
    private String job;

    public EmotionStatisticsDto(String emotionType, long count, double ratio) {
        this.emotionType = emotionType;
        this.count = count;
        this.ratio = ratio;
    }

    public EmotionStatisticsDto(String job, String emotionType, long count, double ratio) {
        this.job = job;
        this.emotionType = emotionType;
        this.count = count;
        this.ratio = ratio;
    }

    public String getEmotionType() {
        return emotionType;
    }

    public long getCount() {
        return count;
    }

    public double getRatio() {
        return ratio;
    }

    public String getJob() {
        return job;
    }

}
