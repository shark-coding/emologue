package com.project.emologue;

import com.project.emologue.external.DictionaryEmotionClient;
import com.project.emologue.external.PredictorEmotionClient;
import com.project.emologue.model.emotion.AIPredicted.AIPredictedEmotionResult;
import com.project.emologue.model.emotion.Dictionary.DictionaryEmotionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DictionaryTest {

    @Autowired
    private DictionaryEmotionClient dictionaryClient;
    @Autowired
    private PredictorEmotionClient predictorClient;

    @Test
    public void testAnalyze() {
        DictionaryEmotionResult result = dictionaryClient.analyze("오늘 하루가 너무 힘들었어");

        System.out.println("감정 타입: " + result.getEmotionType());
        System.out.println("점수: " + result.getPolarityScore());
    }

    @Test
    public void testPredictor() {
        AIPredictedEmotionResult result = predictorClient.predictor("오늘 하루 너무 더워서 힘들었지만, 운동을 하고 나니 개운해서 기분이 좋았어.");
        System.out.println("감정 타입: " + result.getEmotionType());
        System.out.println("신뢰 점수: " + result.getConfidenceScore());
    }
}

