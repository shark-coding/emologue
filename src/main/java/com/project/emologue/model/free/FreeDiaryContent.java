package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;
import com.project.emologue.model.entity.FreeDiaryContentEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record FreeDiaryContent(
        @Schema(description = "freeDiaryContent PK")
        Long freeDiaryContentId,

        @Schema(description = "자유형 내용")
        String content,

        @Schema(description = "AI 감정 분석 결과")
        EmotionType aIEmotionType,

        @Schema(description = "감정 신뢰도")
        Double confidenceScore,

        @Schema(description = "네 번째 답변(감정 번호)")
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
