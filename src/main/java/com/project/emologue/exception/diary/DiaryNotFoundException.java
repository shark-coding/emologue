package com.project.emologue.exception.diary;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class DiaryNotFoundException extends ClientErrorException {
    public DiaryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "DiaryType not found");
    }

    public DiaryNotFoundException(Long diaryId) {
        super(HttpStatus.NOT_FOUND, "Answer with diaryId " + diaryId + " not found");
    }

    public DiaryNotFoundException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
