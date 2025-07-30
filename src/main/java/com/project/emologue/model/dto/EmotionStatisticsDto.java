package com.project.emologue.model.dto;

public class EmotionStatisticsDto {
    private String emotionType;
    private long count;
    private double ratio;
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
