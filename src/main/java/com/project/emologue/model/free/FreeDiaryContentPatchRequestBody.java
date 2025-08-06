package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record FreeDiaryContentPatchRequestBody(
        @Schema(description = "자유형 내용")
        String content,

        @Schema(description = "AI 감정 분석 결과")
        EmotionType aiEmotionType,

        @Schema(description = "감정 신뢰도")
        Double confidenceScore,

        @Schema(description = "네 번째 답변(감정 번호)")
        Long secondAnswer) {
}
