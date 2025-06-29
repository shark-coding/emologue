package com.project.emologue.service;

import com.project.emologue.exception.diary.DiaryNotFoundException;
import com.project.emologue.exception.question.QuestionAnswerNotFoundException;
import com.project.emologue.model.diary.DiaryEntityPatchRequestBody;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.entity.DiaryEntity;
import com.project.emologue.model.entity.EmotionEntity;
import com.project.emologue.model.entity.QuestionAnswerEntity;
import com.project.emologue.model.question.QuestionAnswer;
import com.project.emologue.model.question.QuestionAnswerPatchRequestBody;
import com.project.emologue.model.question.QuestionAnswerPostRequestBody;
import com.project.emologue.repository.QuestionAnswerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class QuestionAnswerService {

    @Autowired private QuestionAnswerEntityRepository questionAnswerEntityRepository;
    @Autowired private EmotionService emotionService;


    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswerEntityRepository.findAll().stream().map(QuestionAnswer::from).toList();
    }

    public QuestionAnswer getQuestionAnswerByQuestionAnswerId(Long questionAnswerId) {
        QuestionAnswerEntity questionAnswer = getQuestionAnswerEntityByQuestionAnswerId(questionAnswerId);
        return QuestionAnswer.from(questionAnswer);
    }

    public QuestionAnswerEntity getQuestionAnswerEntityByQuestionAnswerId(Long questionAnswerId) {
        return questionAnswerEntityRepository.findByQuestionAnswerId(questionAnswerId)
                .orElseThrow(() ->
                        new QuestionAnswerNotFoundException(questionAnswerId));
    }

    public QuestionAnswerEntity getQuestionAnswerEntityByDiaryId(Long diaryId) {
        return questionAnswerEntityRepository.findByDiary_DiaryId(diaryId)
                .orElseThrow(() ->
                        new DiaryNotFoundException(diaryId));
    }

    public QuestionAnswerEntity createQuestionAnswer(DiaryEntityPostRequestBody requestBody) {
        QuestionAnswerPostRequestBody questionAnswer = requestBody.questionAnswer();
        EmotionEntity firstEmotion = emotionService.getEmotionEntityByEmotionId(questionAnswer.firstAnswer());
        EmotionEntity fourthEmotion = emotionService.getEmotionEntityByEmotionId(questionAnswer.fourthAnswer());
        return QuestionAnswerEntity.of(firstEmotion
                , questionAnswer.secondAnswer(), questionAnswer.thirdAnswer(), fourthEmotion);
    }

    public QuestionAnswerEntity updateQuestionAnswer(Long diaryId, DiaryEntityPatchRequestBody requestBody) {
        QuestionAnswerPatchRequestBody questionAnswer = requestBody.questionAnswer();
        EmotionEntity firstEmotion = emotionService.getEmotionEntityByEmotionId(questionAnswer.firstAnswer());
        EmotionEntity fourthEmotion = emotionService.getEmotionEntityByEmotionId(questionAnswer.fourthAnswer());

        QuestionAnswerEntity questionAnswerEntity = getQuestionAnswerEntityByDiaryId(diaryId);
        if (!ObjectUtils.isEmpty(questionAnswer.firstAnswer())) {
            questionAnswerEntity.setFirstAnswer(firstEmotion);
        }
        if (!ObjectUtils.isEmpty(questionAnswer.secondAnswer())) {
            questionAnswerEntity.setSecondAnswer(questionAnswer.secondAnswer());
        }
        if (!ObjectUtils.isEmpty(questionAnswer.thirdAnswer())) {
            questionAnswerEntity.setThirdAnswer(questionAnswer.thirdAnswer());
        }
        if (!ObjectUtils.isEmpty(questionAnswer.fourthAnswer())) {
            questionAnswerEntity.setFourthAnswer(fourthEmotion);
        }

        return questionAnswerEntity;
    }
}
