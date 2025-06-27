package com.project.emologue.exception.emotion;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class EmotionNotFoundException extends ClientErrorException {
    public EmotionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "name not found");
    }

    public EmotionNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Emotion with name " + name + " not found");
    }

    public EmotionNotFoundException(Long emotionId) {
        super(HttpStatus.NOT_FOUND, "Emotion with emotionId " + emotionId + " not found");
    }
}
