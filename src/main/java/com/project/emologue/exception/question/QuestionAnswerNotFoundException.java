package com.project.emologue.exception.question;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class QuestionAnswerNotFoundException extends ClientErrorException {
    public QuestionAnswerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Jobname not found");
    }

//    public QuestionAnswerNotFoundException(String jobname) {
//        super(HttpStatus.NOT_FOUND, "QuestionAnswer with jobname " + jobname + " not found");
//    }

    public QuestionAnswerNotFoundException(Long questionAnswerId) {
        super(HttpStatus.NOT_FOUND, "QuestionAnswer with questionAnswerId " + questionAnswerId + " not found");
    }
}
