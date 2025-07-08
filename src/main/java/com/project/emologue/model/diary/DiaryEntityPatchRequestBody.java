package com.project.emologue.model.diary;

import com.project.emologue.model.free.FreeDiaryContentPatchRequestBody;
import com.project.emologue.model.question.QuestionAnswerPatchRequestBody;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

public record DiaryEntityPatchRequestBody(
        DiaryType type,
        @Valid QuestionAnswerPatchRequestBody questionAnswer,
        @Valid FreeDiaryContentPatchRequestBody freeDiaryContent) {

    public void validate() {
        if (type == DiaryType.QUESTION && questionAnswer == null) {
            throw new ValidationException("문답형 다이어리는 questionAnswer가 필요합니다.");
        }
        if (type == DiaryType.FREE && freeDiaryContent == null) {
            throw new ValidationException("자유형 다이어리는 freeDiaryContent가 필요합니다.");
        }
        if (questionAnswer != null && freeDiaryContent != null) {
            throw new ValidationException("문답형과 자유형은 동시에 제출할 수 없습니다.");
        }
    }
}
