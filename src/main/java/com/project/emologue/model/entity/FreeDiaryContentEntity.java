package com.project.emologue.model.entity;

import com.project.emologue.model.emotion.EmotionType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "free")
public class FreeDiaryContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeDiaryContentId;

    @OneToOne
    @JoinColumn(name = "free_diary_id", nullable = false)
    private DiaryEntity diary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ai_predicted_emotion_id")
    private AIPredictedEmotionEntity aiPredictedEmotionEntity;

    @ManyToOne
    @JoinColumn(name = "second_emotion_id")
    private EmotionEntity secondAnswer;

    @Column
    private LocalDate createdDateTime;

    public Long getFreeDiaryContentId() {
        return freeDiaryContentId;
    }

    public void setFreeDiaryContentId(Long freeDiaryContentId) {
        this.freeDiaryContentId = freeDiaryContentId;
    }

    public DiaryEntity getDiary() {
        return diary;
    }

    public void setDiary(DiaryEntity diary) {
        this.diary = diary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AIPredictedEmotionEntity getAiPredictedEmotionEntity() {
        return aiPredictedEmotionEntity;
    }

    public void setAiPredictedEmotionEntity(AIPredictedEmotionEntity aiPredictedEmotionEntity) {
        this.aiPredictedEmotionEntity = aiPredictedEmotionEntity;
    }

    public EmotionEntity getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(EmotionEntity secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreeDiaryContentEntity that = (FreeDiaryContentEntity) o;
        return Objects.equals(freeDiaryContentId, that.freeDiaryContentId) && Objects.equals(diary, that.diary) && Objects.equals(content, that.content) && Objects.equals(aiPredictedEmotionEntity, that.aiPredictedEmotionEntity) && Objects.equals(secondAnswer, that.secondAnswer) && Objects.equals(createdDateTime, that.createdDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(freeDiaryContentId, diary, content, aiPredictedEmotionEntity, secondAnswer, createdDateTime);
    }

    public static FreeDiaryContentEntity of(String content, EmotionType aiEmotionType, Double confidenceScore, EmotionEntity secondAnswer) {
        FreeDiaryContentEntity freeDiaryContentEntity = new FreeDiaryContentEntity();
        freeDiaryContentEntity.setContent(content);
        freeDiaryContentEntity.setSecondAnswer(secondAnswer);

        AIPredictedEmotionEntity aiPredictedEmotion = AIPredictedEmotionEntity.of(aiEmotionType, confidenceScore);
        freeDiaryContentEntity.setAiPredictedEmotionEntity(aiPredictedEmotion);

        return freeDiaryContentEntity;
    }

    @PrePersist
    private void prePersist() {
        createdDateTime = LocalDate.now();
    }
}
