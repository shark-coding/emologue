package com.project.emologue.model.entity;

import com.project.emologue.model.emotion.EmotionType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ai_emotion")
public class AIPredictedEmotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aIPredictedEmotionId;

    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(nullable = false)
    private Double confidenceScore;

    @OneToOne(mappedBy = "aiPredictedEmotionEntity")
    private FreeDiaryContentEntity freeDiaryContentEntity;

    public Long getaIPredictedEmotionId() {
        return aIPredictedEmotionId;
    }

    public void setaIPredictedEmotionId(Long aIPredictedEmotionId) {
        this.aIPredictedEmotionId = aIPredictedEmotionId;
    }

    public EmotionType getEmotionType() {
        return emotionType;
    }

    public void setEmotionType(EmotionType emotionType) {
        this.emotionType = emotionType;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public FreeDiaryContentEntity getFreeDiaryContentEntity() {
        return freeDiaryContentEntity;
    }

    public void setFreeDiaryContentEntity(FreeDiaryContentEntity freeDiaryContentEntity) {
        this.freeDiaryContentEntity = freeDiaryContentEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIPredictedEmotionEntity that = (AIPredictedEmotionEntity) o;
        return Objects.equals(aIPredictedEmotionId, that.aIPredictedEmotionId) && emotionType == that.emotionType && Objects.equals(confidenceScore, that.confidenceScore) && Objects.equals(freeDiaryContentEntity, that.freeDiaryContentEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aIPredictedEmotionId, emotionType, confidenceScore, freeDiaryContentEntity);
    }

    public static AIPredictedEmotionEntity of(EmotionType emotionType, Double confidenceScore) {
        var aiPredictedEmotionEntity = new AIPredictedEmotionEntity();
        aiPredictedEmotionEntity.setEmotionType(emotionType);
        aiPredictedEmotionEntity.setConfidenceScore(confidenceScore);
        return aiPredictedEmotionEntity;
    }
}
