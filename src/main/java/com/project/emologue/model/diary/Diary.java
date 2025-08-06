package com.project.emologue.model.diary;

import com.project.emologue.model.entity.DiaryEntity;
import com.project.emologue.model.free.FreeDiaryContent;
import com.project.emologue.model.question.QuestionAnswer;
import io.swagger.v3.oas.annotations.media.Schema;

public record Diary(
        @Schema(description = "다이어리 PK", example = "1")
        Long diaryId,
        @Schema(description = "사용자 PK", example = "1")
        Long userId,
        @Schema(description = "다이어리 타입(QUESTION or FREE)", example = "QUESTION")
        DiaryType type,
        @Schema(description = "질문형 다이어리 답변 (type이 QUESTION일 때만 값이 존재)")
        QuestionAnswer questionAnswer,
        @Schema(description = "자유형 다이어리 내용 (type이 FREE일 때만 값이 존재)", example = "null")
        FreeDiaryContent freeDiaryContent) {

    public static Diary fromQuestion(DiaryEntity entity) {
        return new Diary(
                entity.getDiaryId(),
                entity.getUser().getUserId(),
                entity.getType(),
                QuestionAnswer.from(entity.getQuestionAnswer()),
                null // FreeDiaryContent은 없음
        );
    }

    public static Diary fromFree(DiaryEntity entity) {
        return new Diary(
                entity.getDiaryId(),
                entity.getUser().getUserId(),
                entity.getType(),
                null, // QuestionAnswer는 없음
                FreeDiaryContent.from(entity.getFreeDiaryContent())
        );
    }

    public static Diary from(DiaryEntity entity) {
        return new Diary(
                entity.getDiaryId(),
                entity.getUser().getUserId(),
                entity.getType(),
                entity.getType() == DiaryType.QUESTION ? QuestionAnswer.from(entity.getQuestionAnswer()) : null,
                entity.getType() == DiaryType.FREE ? FreeDiaryContent.from(entity.getFreeDiaryContent()) : null
        );
    }
}
