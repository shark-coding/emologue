package com.project.emologue.model.entity;

import com.project.emologue.model.emotion.EmotionType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "emotion",
        indexes = {
                @Index(name = "emotion_name_idx", columnList = "name", unique = true)})
public class EmotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @Column(nullable = false)
    private String description;

    public Long getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(Long emotionId) {
        this.emotionId = emotionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmotionType getEmotionType() {
        return emotionType;
    }

    public void setEmotionType(EmotionType emotionType) {
        this.emotionType = emotionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmotionEntity that = (EmotionEntity) o;
        return Objects.equals(emotionId, that.emotionId) && Objects.equals(name, that.name) && emotionType == that.emotionType && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emotionId, name, emotionType, description);
    }

    public static EmotionEntity of(String name, EmotionType emotionType, String description) {
        var emotionEntity = new EmotionEntity();
        emotionEntity.setName(name);
        emotionEntity.setEmotionType(emotionType);
        emotionEntity.setDescription(description);
        return emotionEntity;
    }
}
