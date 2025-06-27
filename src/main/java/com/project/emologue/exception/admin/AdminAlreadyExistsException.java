package com.project.emologue.exception.admin;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class AdminAlreadyExistsException extends ClientErrorException {
    public AdminAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Admin already exists");
    }

    public AdminAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT, "Admin with username " + username + " already exists");
    }
}
