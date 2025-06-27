package com.project.emologue.model.emotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EmotionRequestBody(
        @NotEmpty  String name,
        @NotNull @JsonProperty("emotionType") EmotionType emotionType,
        @NotEmpty String description) {
}
