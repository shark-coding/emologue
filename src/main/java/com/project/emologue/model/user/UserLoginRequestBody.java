package com.project.emologue.model.user;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(
        @NotEmpty String username,
        @NotEmpty String password) {}
