package com.project.emologue.model.emotion;

import com.project.emologue.model.entity.EmotionEntity;

public record Emotion(
        Long emotionId,
        String name,
        EmotionType emotionType,
        String description) {
    public static Emotion from(EmotionEntity emotionEntity) {
        return new Emotion(
                emotionEntity.getEmotionId(),
                emotionEntity.getName(),
                emotionEntity.getEmotionType(),
                emotionEntity.getDescription());
    }
}
