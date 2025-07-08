package com.project.emologue.model.emotion.AIPredicted;

import com.project.emologue.model.emotion.EmotionType;
import com.project.emologue.model.entity.AIPredictedEmotionEntity;

public record AIPredictedEmotion(
        Long aIPredictedEmotionId,
        EmotionType emotionType,
        Double confidenceScore) {
    public static AIPredictedEmotion from(AIPredictedEmotionEntity aiPredictedEmotionEntity) {
        return new AIPredictedEmotion(
                aiPredictedEmotionEntity.getaIPredictedEmotionId(),
                aiPredictedEmotionEntity.getEmotionType(),
                aiPredictedEmotionEntity.getConfidenceScore());
    }
}