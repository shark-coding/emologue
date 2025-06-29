package com.project.emologue.exception.diary;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class DiaryAlreadyExistsException extends ClientErrorException {

    public DiaryAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
