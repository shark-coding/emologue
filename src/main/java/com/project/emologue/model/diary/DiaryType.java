package com.project.emologue.model.diary;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DiaryType {
    QUESTION,
    FREE;

    @JsonCreator
    public static DiaryType from(String value) {
        return DiaryType.valueOf(value.toUpperCase());
    }
}
