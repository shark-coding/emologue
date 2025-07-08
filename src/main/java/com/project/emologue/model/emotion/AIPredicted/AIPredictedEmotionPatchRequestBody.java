package com.project.emologue.model.emotion.AIPredicted;

import com.project.emologue.model.emotion.EmotionType;

public record AIPredictedEmotionPatchRequestBody(
        Long aIPredictedEmotionId,
        EmotionType emotionType,
        Double confidenceScore) {
}