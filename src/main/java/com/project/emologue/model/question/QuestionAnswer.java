package com.project.emologue.model.question;

import com.project.emologue.model.entity.QuestionAnswerEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionAnswer(
        @Schema(description = "questionAnswer PK", example = "1")
        Long questionAnswerId,

        @Schema(description = "첫 번째 답변(감정 번호)", example = "1")
        Long firstAnswer,

        @Schema(description = "두 번째 답변", example = "오늘 하루종일 너무 더워. 조금만 움직여도 땀이 너무 많이 나는 하루였어. 나도 이러는데 다른 사람들도 그랬겠지? 그치만 나는 남한테 짜증을 내지 않았어. 지하철을 기다리는데 더운건 이해하지만 옆 사람한테 짜증내는 사람을 보고 나도 덩달아 짜증이 나더라.")
        String secondAnswer,

        @Schema(description = "세 번째 답변", example = "오늘 나에게 따뜻했던 순간은 없었던 것 같아. 나를 더 사랑해줘야겠다는 반성이 되는 순간이야.")
        String thirdAnswer,

        @Schema(description = "네 번째 답변(감정 번호)", example = "22")
        Long fourthAnswer) {

    public static QuestionAnswer from(QuestionAnswerEntity questionAnswerEntity) {
        return new QuestionAnswer(
                questionAnswerEntity.getQuestionAnswerId(),
                questionAnswerEntity.getFirstAnswer().getEmotionId(),
                questionAnswerEntity.getSecondAnswer(),
                questionAnswerEntity.getThirdAnswer(),
                questionAnswerEntity.getFourthAnswer().getEmotionId());
    }
}
