package com.project.emologue.model.emotion;

import com.project.emologue.model.entity.EmotionEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record Emotion(
        @Schema(description = "감정 PK", example = "11")
        Long emotionId,

        @Schema(description = "감정 이름", example = "행복")
        String name,

        @Schema(description = "감정 타입", example = "POSITIVE")
        EmotionType emotionType,

        @Schema(description = "감정 설명", example = "행복")
        String description) {
    public static Emotion from(EmotionEntity emotionEntity) {
        return new Emotion(
                emotionEntity.getEmotionId(),
                emotionEntity.getName(),
                emotionEntity.getEmotionType(),
                emotionEntity.getDescription());
    }
}
