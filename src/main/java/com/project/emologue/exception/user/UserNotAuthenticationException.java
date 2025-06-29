package com.project.emologue.exception.user;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAuthenticationException extends ClientErrorException {

    public UserNotAuthenticationException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
