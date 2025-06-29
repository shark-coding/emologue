package com.project.emologue.service;

import com.project.emologue.exception.emotion.EmotionAlreadyExistsException;
import com.project.emologue.exception.emotion.EmotionNotFoundException;
import com.project.emologue.model.emotion.Emotion;
import com.project.emologue.model.emotion.EmotionPatchRequestBody;
import com.project.emologue.model.emotion.EmotionPostRequestBody;
import com.project.emologue.model.entity.EmotionEntity;
import com.project.emologue.repository.EmotionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EmotionService {

    @Autowired private EmotionEntityRepository emotionEntityRepository;


    public List<Emotion> getEmotions() {
        List<EmotionEntity> emotionEntities = emotionEntityRepository.findAll();
        return emotionEntities.stream().map(Emotion::from).toList();
    }

    public Emotion getEmotionByEmotionId(Long emotionId) {
        EmotionEntity emotionEntity =getEmotionEntityByEmotionId(emotionId);
        return Emotion.from(emotionEntity);
    }

    public EmotionEntity getEmotionEntityByEmotionId(Long emotionId) {
        return emotionEntityRepository.findByEmotionId(emotionId)
                .orElseThrow(() -> new EmotionNotFoundException(emotionId));
    }

    public boolean getEmotionEntityByName(String name) {
        return emotionEntityRepository.findByName(name).isPresent();
    }


    public Emotion createEmotion(EmotionPostRequestBody createEmotion) {
        emotionEntityRepository.findByName(createEmotion.name())
                .ifPresent(
                        emotions -> {
                            throw new EmotionAlreadyExistsException();
                        }
                );

        var emotionEntity = EmotionEntity.of(
                createEmotion.name(),
                createEmotion.emotionType(),
                createEmotion.description());
        return Emotion.from(emotionEntityRepository.save(emotionEntity));
    }


    public Emotion updateEmotion(Long emotionId, EmotionPatchRequestBody updateEmotion) {
        EmotionEntity emotionEntity = getEmotionEntityByEmotionId(emotionId);
        if (!ObjectUtils.isEmpty(updateEmotion.name())) {
            if (getEmotionEntityByName(updateEmotion.name())) {
                throw new EmotionAlreadyExistsException();
            }
            emotionEntity.setName(updateEmotion.name());
        }
        if (!ObjectUtils.isEmpty(updateEmotion.emotionType())) {
            emotionEntity.setEmotionType(updateEmotion.emotionType());
        }
        if (!ObjectUtils.isEmpty(updateEmotion.description())) {
            emotionEntity.setDescription(updateEmotion.description());
        }
        return Emotion.from(emotionEntityRepository.save(emotionEntity));
    }


    public void deleteEmotion(long emotionId) {
        EmotionEntity emotionEntity = getEmotionEntityByEmotionId(emotionId);
        emotionEntityRepository.delete(emotionEntity);
    }
}
