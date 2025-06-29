package com.project.emologue.model.entity;

import com.project.emologue.model.diary.DiaryType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "diary")
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private LocalDate createdDateTime;

    @Enumerated(EnumType.STRING)
    private DiaryType type;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private QuestionAnswerEntity questionAnswer;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private FreeDiaryContentEntity freeDiaryContent;

    public Long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Long diaryId) {
        this.diaryId = diaryId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public DiaryType getType() {
        return type;
    }

    public void setType(DiaryType type) {
        this.type = type;
    }

    public QuestionAnswerEntity getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(QuestionAnswerEntity questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public FreeDiaryContentEntity getFreeDiaryContent() {
        return freeDiaryContent;
    }

    public void setFreeDiaryContent(FreeDiaryContentEntity freeDiaryContent) {
        this.freeDiaryContent = freeDiaryContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntity that = (DiaryEntity) o;
        return Objects.equals(diaryId, that.diaryId) && Objects.equals(user, that.user) && Objects.equals(createdDateTime, that.createdDateTime) && type == that.type && Objects.equals(questionAnswer, that.questionAnswer) && Objects.equals(freeDiaryContent, that.freeDiaryContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaryId, user, createdDateTime, type, questionAnswer, freeDiaryContent);
    }

    @PrePersist
    private void prePersist() {
        this.createdDateTime = LocalDate.now();
    }

    public static DiaryEntity ofQuestion(
            UserEntity user, DiaryType type, QuestionAnswerEntity questionAnswerEntity) {
        DiaryEntity diary = new DiaryEntity();
        diary.setUser(user);
        diary.setType(type);
        diary.setQuestionAnswer(questionAnswerEntity);
        questionAnswerEntity.setDiary(diary);
        return diary;
    }

//    public static DiaryEntity ofFree(
//            UserEntity user, DiaryType type, FreeDiaryContentEntity freeDiaryContentEntity) {
//        DiaryEntity diary = new DiaryEntity();
//        diary.setUser(user);
//        diary.setType(type);
//        diary.setFreeDiaryContent(freeDiaryContentEntity);
//        freeDiaryContentEntity.setDiary(diary);
//        return diary;
//    }

}
