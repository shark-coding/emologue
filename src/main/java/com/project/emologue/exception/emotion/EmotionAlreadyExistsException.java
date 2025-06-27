package com.project.emologue.exception.emotion;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class EmotionAlreadyExistsException extends ClientErrorException {
    public EmotionAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "name already exists");
    }

    public EmotionAlreadyExistsException(String name) {
        super(HttpStatus.CONFLICT, "Emotion with name " + name + " already exists");
    }
}
