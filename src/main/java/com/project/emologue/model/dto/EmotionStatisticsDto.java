package com.project.emologue.model.dto;

import java.util.Objects;

public class EmotionStatisticsDto {
    private String emotionType;
    private long count;
    private double ratio;

    public EmotionStatisticsDto(String emotionType, long count, double ratio) {
        this.emotionType = emotionType;
        this.count = count;
        this.ratio = ratio;
    }

    public String getEmotionType() {
        return emotionType;
    }

    public void setEmotionType(String emotionType) {
        this.emotionType = emotionType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmotionStatisticsDto that = (EmotionStatisticsDto) o;
        return count == that.count && Double.compare(ratio, that.ratio) == 0 && emotionType == that.emotionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emotionType, count, ratio);
    }
}
