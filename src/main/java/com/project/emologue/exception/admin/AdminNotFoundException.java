package com.project.emologue.exception.admin;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends ClientErrorException {
    public AdminNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Admin not found");
    }

    public AdminNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "Admin with username " + username + " not found");
    }
}
