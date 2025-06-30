package com.project.emologue.service;

import com.project.emologue.exception.emotion.EmotionAlreadyExistsException;
import com.project.emologue.exception.emotion.EmotionNotFoundException;
import com.project.emologue.model.emotion.Emotion;
import com.project.emologue.model.emotion.EmotionPatchRequestBody;
import com.project.emologue.model.emotion.EmotionPostRequestBody;
import com.project.emologue.model.emotion.EmotionType;
import com.project.emologue.model.entity.EmotionEntity;
import com.project.emologue.repository.EmotionEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmotionServiceTest {
    @Mock private EmotionEntityRepository emotionEntityRepository;

    @InjectMocks private EmotionService emotionService;

    @Test
    void 전체_조회() {
        List<EmotionEntity> entities = List.of(
                EmotionEntity.of("기쁨", EmotionType.POSITIVE, "기쁜 감정"),
                EmotionEntity.of("슬픔", EmotionType.NEGATIVE, "슬픈 감정"));

        when(emotionEntityRepository.findAll()).thenReturn(entities);

        List<Emotion> result = emotionService.getEmotions();

        assertEquals(2, result.size());
        assertEquals("기쁨", result.get(0).name());
    }

    @Test
    void emotionId_존재하면_조회성공() {
        Long emotionId = 1L;
        EmotionEntity entity = EmotionEntity.of("분노", EmotionType.NEGATIVE, "화가난다.");
        entity.setEmotionId(emotionId);

        when(emotionEntityRepository.findByEmotionId(emotionId))
                .thenReturn(Optional.of(entity));

        Emotion result = emotionService.getEmotionByEmotionId(emotionId);

        assertEquals("분노", result.name());
    }

    @Test
    void emotionId_존재하지_않으면_예외() {
        Long emotionId = 1L;
        when(emotionEntityRepository.findByEmotionId(emotionId))
                .thenReturn(Optional.empty());

        assertThrows(EmotionNotFoundException.class, () -> {
            emotionService.getEmotionByEmotionId(emotionId);
        });
    }

    @Test
    void emotionName_존재하면_true() {
        when(emotionEntityRepository.findByName("기쁨")).thenReturn(Optional.of(mock(EmotionEntity.class)));
        boolean result = emotionService.getEmotionEntityByName("기쁨");
        assertTrue(result);
    }

    @Test
    void emotionName_없으면_예외() {
        when(emotionEntityRepository.findByName("공포")).thenReturn(Optional.empty());
        boolean result = emotionService.getEmotionEntityByName("공포");
        assertFalse(result);
    }

    @Test
    void create_성공() {
        EmotionPostRequestBody postRequest = new EmotionPostRequestBody("감동", EmotionType.NEGATIVE, "찡 한 느낌");
        when(emotionEntityRepository.findByName("감동")).thenReturn(Optional.empty());

        EmotionEntity savedEntity = EmotionEntity.of("감동", EmotionType.POSITIVE,"찡 한 느낌");
        savedEntity.setEmotionId(1L);
        when(emotionEntityRepository.save(any())).thenReturn(savedEntity);

        Emotion result = emotionService.createEmotion(postRequest);

        assertEquals("감동", result.name());
        assertEquals(EmotionType.POSITIVE, result.emotionType());
    }

    @Test
    void createEmotion_중복시_예외() {
        EmotionPostRequestBody postRequest = new EmotionPostRequestBody("감동", EmotionType.NEGATIVE, "찡 한 느낌");
        when(emotionEntityRepository.findByName("감동")).thenReturn(Optional.of(new EmotionEntity()));
        assertThrows(EmotionAlreadyExistsException.class, () ->
                emotionService.createEmotion(postRequest));
    }

    @Test
    void updateEmotion_성공() {
        Long emotionId = 1L;
        EmotionEntity existing = EmotionEntity.of("기쁨", EmotionType.POSITIVE, "기존 설명");
        existing.setEmotionId(emotionId);

        when(emotionEntityRepository.findByEmotionId(emotionId)).thenReturn(Optional.of(existing));
        when(emotionEntityRepository.findByName("행복")).thenReturn(Optional.empty());
        when(emotionEntityRepository.save(any())).thenReturn(existing);

        EmotionPatchRequestBody update = new EmotionPatchRequestBody("행복", EmotionType.POSITIVE, "수정됨");

        Emotion result = emotionService.updateEmotion(emotionId, update);

        assertEquals("행복", result.name());
        assertEquals("수정됨", result.description());
    }

    @Test
    void updateEmotion_중복_예외() {
        Long emotionId = 1L;
        EmotionEntity existing = EmotionEntity.of("기쁨", EmotionType.POSITIVE, "기존 설명");
        existing.setEmotionId(emotionId);

        when(emotionEntityRepository.findByEmotionId(emotionId)).thenReturn(Optional.of(existing));
        when(emotionEntityRepository.findByName("슬픔")).thenReturn(Optional.of(new EmotionEntity()));

        EmotionPatchRequestBody update = new EmotionPatchRequestBody("슬픔", null, null);

        assertThrows(EmotionAlreadyExistsException.class, () ->
                emotionService.updateEmotion(emotionId, update)
        );
    }

    @Test
    void deleteEmotion_성공() {
        Long emotionId = 1L;
        EmotionEntity entity = EmotionEntity.of("혐오", EmotionType.NEGATIVE, "싫은 감정");
        entity.setEmotionId(emotionId);

        when(emotionEntityRepository.findByEmotionId(emotionId)).thenReturn(Optional.of(entity));

        emotionService.deleteEmotion(emotionId);

        verify(emotionEntityRepository, times(1)).delete(entity);
    }

}
