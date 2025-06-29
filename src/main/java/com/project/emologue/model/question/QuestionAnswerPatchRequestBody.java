package com.project.emologue.model.question;

public record QuestionAnswerPatchRequestBody(
        Long firstAnswer,
        String secondAnswer,
        String thirdAnswer,
        Long fourthAnswer) {
}
