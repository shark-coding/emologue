package com.project.emologue.model.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuestionAnswerPostRequestBody(
        @NotNull Long firstAnswer,
        @NotEmpty String secondAnswer,
        @NotEmpty String thirdAnswer,
        @NotNull Long fourthAnswer) {
}
