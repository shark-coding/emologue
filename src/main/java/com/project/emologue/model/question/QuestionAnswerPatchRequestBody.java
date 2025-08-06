package com.project.emologue.model.question;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionAnswerPatchRequestBody(
        @Schema(description = "첫 번째 답변(감정 번호)", example = "2")
        Long firstAnswer,

        @Schema(description = "두 번째 답변", example = "오늘 길을 걷다가 넘어졌어. 너무 크게 넘어져서 부끄럽고 창피했지.")
        String secondAnswer,

        @Schema(description = "세 번째 답변", example = "오늘 나는 나에게 맛있는 디저트를 사줬어. 맛있었다!")
        String thirdAnswer,

        @Schema(description = "네 번째 답변(감정 번호)", example = "22")
        Long fourthAnswer) {
}
