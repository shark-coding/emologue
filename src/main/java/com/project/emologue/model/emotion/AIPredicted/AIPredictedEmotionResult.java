package com.project.emologue.model.emotion.AIPredicted;

import com.project.emologue.model.emotion.EmotionType;

public class AIPredictedEmotionResult {
    private final EmotionType emotionType;
    private final Double confidenceScore;

    public EmotionType getEmotionType() {
        return emotionType;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public AIPredictedEmotionResult(EmotionType emotionType, Double confidenceScore) {
        this.emotionType = emotionType;
        this.confidenceScore = confidenceScore;
    }
}
