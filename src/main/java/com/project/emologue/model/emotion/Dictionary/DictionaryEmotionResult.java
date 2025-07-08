package com.project.emologue.model.emotion.Dictionary;

import com.project.emologue.model.emotion.EmotionType;

public class DictionaryEmotionResult {
    private final EmotionType emotionType;
    private final Double polarityScore;

    public EmotionType getEmotionType() {
        return emotionType;
    }

    public Double getPolarityScore() {
        return polarityScore;
    }

    public DictionaryEmotionResult(EmotionType emotionType, Double polarityScore) {
        this.emotionType = emotionType;
        this.polarityScore = polarityScore;
    }
}
