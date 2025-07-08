package com.project.emologue.model.emotion.AIPredicted;

import com.project.emologue.model.emotion.EmotionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AIPredictedEmotionPostRequestBody(
        @NotEmpty Long aIPredictedEmotionId,
        @NotNull EmotionType emotionType,
        @NotEmpty Double confidenceScore) {
}