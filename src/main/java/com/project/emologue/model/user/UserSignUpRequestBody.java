package com.project.emologue.model.user;

import com.project.emologue.model.entity.JobEntity;
import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String name,
        @NotEmpty String jobname) {}
