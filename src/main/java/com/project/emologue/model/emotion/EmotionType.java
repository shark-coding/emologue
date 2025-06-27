package com.project.emologue.model.emotion;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EmotionType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL;

    @JsonCreator
    public static EmotionType from(String value) {
        return EmotionType.valueOf(value.toUpperCase());
    }
}
