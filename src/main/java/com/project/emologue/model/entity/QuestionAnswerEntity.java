package com.project.emologue.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "question")
public class QuestionAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionAnswerId;

    @OneToOne
    @JoinColumn(name = "quest_diary_id", nullable = false)
    private DiaryEntity diary;

    @ManyToOne
    @JoinColumn(name = "first_emotion_id")
    private EmotionEntity firstAnswer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String secondAnswer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String thirdAnswer;

    @ManyToOne
    @JoinColumn(name = "fourth_emotion_id")
    private EmotionEntity fourthAnswer;

    @Column
    private LocalDate createdDateTime;

    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public DiaryEntity getDiary() {
        return diary;
    }

    public void setDiary(DiaryEntity diary) {
        this.diary = diary;
    }

    public EmotionEntity getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(EmotionEntity firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public EmotionEntity getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(EmotionEntity fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
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
        QuestionAnswerEntity that = (QuestionAnswerEntity) o;
        return Objects.equals(questionAnswerId, that.questionAnswerId) && Objects.equals(diary, that.diary) && Objects.equals(firstAnswer, that.firstAnswer) && Objects.equals(secondAnswer, that.secondAnswer) && Objects.equals(thirdAnswer, that.thirdAnswer) && Objects.equals(fourthAnswer, that.fourthAnswer) && Objects.equals(createdDateTime, that.createdDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionAnswerId, diary, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, createdDateTime);
    }

    public static QuestionAnswerEntity of(EmotionEntity firstAnswer, String secondAnswer, String thirdAnswer, EmotionEntity fourthAnswer) {
        var questionAnswerEntity = new QuestionAnswerEntity();
        questionAnswerEntity.setFirstAnswer(firstAnswer);
        questionAnswerEntity.setSecondAnswer(secondAnswer);
        questionAnswerEntity.setThirdAnswer(thirdAnswer);
        questionAnswerEntity.setFourthAnswer(fourthAnswer);
        return questionAnswerEntity;
    }

    @PrePersist
    private void prePersist() {
        this.createdDateTime = LocalDate.now();
    }
}
