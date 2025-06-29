package com.project.emologue.model.diary;

import com.project.emologue.model.entity.DiaryEntity;
import com.project.emologue.model.free.FreeDiaryContent;
import com.project.emologue.model.question.QuestionAnswer;

public record Diary(
        Long diaryId,
        Long userId,
        DiaryType type,
        QuestionAnswer questionAnswer,
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
