package com.project.emologue.exception.job;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class JobAlreadyExistsException extends ClientErrorException {
    public JobAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Jobname already exists");
    }

    public JobAlreadyExistsException(String jobname) {
        super(HttpStatus.CONFLICT, "Job with jobname " + jobname + " already exists");
    }
}
