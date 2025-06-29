package com.project.emologue.model.question;

import com.project.emologue.model.entity.QuestionAnswerEntity;

public record QuestionAnswer(
        Long questionAnswerId,
        Long firstAnswer,
        String secondAnswer,
        String thirdAnswer,
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
