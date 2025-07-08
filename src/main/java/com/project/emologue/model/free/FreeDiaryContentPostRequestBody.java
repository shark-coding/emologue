package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FreeDiaryContentPostRequestBody(
        @NotEmpty String content,
        @Valid EmotionType aiEmotionType,
        @Valid Double confidenceScore,
        @NotNull Long secondAnswer) {
}
