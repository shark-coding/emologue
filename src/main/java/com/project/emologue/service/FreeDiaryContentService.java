package com.project.emologue.service;

import com.project.emologue.exception.diary.DiaryNotFoundException;
import com.project.emologue.external.PredictorEmotionClient;
import com.project.emologue.model.diary.DiaryEntityPatchRequestBody;
import com.project.emologue.model.diary.DiaryEntityPostRequestBody;
import com.project.emologue.model.emotion.AIPredicted.AIPredictedEmotionResult;
import com.project.emologue.model.entity.AIPredictedEmotionEntity;
import com.project.emologue.model.entity.EmotionEntity;
import com.project.emologue.model.entity.FreeDiaryContentEntity;
import com.project.emologue.model.free.FreeDiaryContent;
import com.project.emologue.model.free.FreeDiaryContentPatchRequestBody;
import com.project.emologue.model.free.FreeDiaryContentPostRequestBody;
import com.project.emologue.repository.FreeDiaryContentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class FreeDiaryContentService {

    @Autowired private FreeDiaryContentEntityRepository freeDiaryContentEntityRepository;
    @Autowired private EmotionService emotionService;
    @Autowired private PredictorEmotionClient predictorClient;

    public FreeDiaryContentEntity createFreeContent(DiaryEntityPostRequestBody requestBody) {
        FreeDiaryContentPostRequestBody freeContent = requestBody.freeDiaryContent();
        String content = freeContent.content();
        EmotionEntity secondAnswer = emotionService.getEmotionEntityByEmotionId(freeContent.secondAnswer());
        AIPredictedEmotionResult result = predictorClient.predictor(content);
        return FreeDiaryContentEntity.of(content, result.getEmotionType(), result.getConfidenceScore(), secondAnswer);
    }

    public FreeDiaryContentEntity updateQuestionAnswer(Long diaryId, DiaryEntityPatchRequestBody requestBody) {
        FreeDiaryContentPatchRequestBody freeContent = requestBody.freeDiaryContent();
        String content = freeContent.content();
        EmotionEntity secondAnswer = emotionService.getEmotionEntityByEmotionId(freeContent.secondAnswer());

        FreeDiaryContentEntity freeDiaryContentEntity = getFreeDiaryContentEntityByDiaryId(diaryId);
        if (!ObjectUtils.isEmpty(freeContent.content())) {
            freeDiaryContentEntity.setContent(freeContent.content());
            AIPredictedEmotionResult result = predictorClient.predictor(content);
            AIPredictedEmotionEntity entity = AIPredictedEmotionEntity.of(result.getEmotionType(), result.getConfidenceScore());
            entity.setFreeDiaryContentEntity(freeDiaryContentEntity);
            freeDiaryContentEntity.setAiPredictedEmotionEntity(entity);
        }
        if (!ObjectUtils.isEmpty(freeContent.secondAnswer())) {
            freeDiaryContentEntity.setSecondAnswer(secondAnswer);
        }

        return freeDiaryContentEntity;
    }

    public FreeDiaryContentEntity getFreeDiaryContentEntityByDiaryId(Long diaryId) {
        return freeDiaryContentEntityRepository.findByDiary_DiaryId(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException(diaryId));
    }
}
