package com.project.emologue.model.emotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmotionPatchRequestBody(
        @Schema(description = "감정 이름", example = "행복")
        String name,

        @JsonProperty("emotionType")
        @Schema(description = "감정 타입", example = "POSITIVE")
        EmotionType emotionType,

        @Schema(description = "감정 설명", example = "행복")
        String description) {
}
