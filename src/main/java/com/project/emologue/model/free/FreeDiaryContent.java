package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;
import com.project.emologue.model.entity.FreeDiaryContentEntity;

public record FreeDiaryContent(
        Long freeDiaryContentId,
        String content,
        EmotionType aIEmotionType,
        Double confidenceScore,
        Long secondAnswer) {

    public static FreeDiaryContent from(FreeDiaryContentEntity freeDiaryContentEntity) {
        return new FreeDiaryContent(
                freeDiaryContentEntity.getFreeDiaryContentId(),
                freeDiaryContentEntity.getContent(),
                freeDiaryContentEntity.getAiPredictedEmotionEntity().getEmotionType(),
                freeDiaryContentEntity.getAiPredictedEmotionEntity().getConfidenceScore(),
                freeDiaryContentEntity.getSecondAnswer().getEmotionId());
    }
}
