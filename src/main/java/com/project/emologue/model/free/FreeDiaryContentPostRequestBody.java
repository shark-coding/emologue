package com.project.emologue.model.free;

import com.project.emologue.model.emotion.EmotionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FreeDiaryContentPostRequestBody(
        @NotEmpty
        @Schema(description = "자유형 내용")
        String content,

        @Valid
        @Schema(description = "AI 감정 분석 결과")
        EmotionType aiEmotionType,

        @Valid
        @Schema(description = "감정 신뢰도")
        Double confidenceScore,

        @NotNull
        @Schema(description = "네 번째 답변(감정 번호)")
        Long secondAnswer) {
}
