package com.project.emologue.model.emotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmotionPatchRequestBody(
        String name,
        @JsonProperty("emotionType") EmotionType emotionType,
        String description) {
}
