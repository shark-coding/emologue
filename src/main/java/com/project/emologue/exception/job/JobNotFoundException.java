package com.project.emologue.exception.job;

import com.project.emologue.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class JobNotFoundException extends ClientErrorException {
    public JobNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Jobname not found");
    }

    public JobNotFoundException(String jobname) {
        super(HttpStatus.NOT_FOUND, "Job with jobname " + jobname + " not found");
    }

    public JobNotFoundException(Long jobId) {
        super(HttpStatus.NOT_FOUND, "Job with jobId " + jobId + " not found");
    }
}
