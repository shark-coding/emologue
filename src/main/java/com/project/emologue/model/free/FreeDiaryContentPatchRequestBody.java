package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;

public record FreeDiaryContentPatchRequestBody(
        String content,
        EmotionType aiEmotionType,
        Double confidenceScore,
        Long secondAnswer) {
}
