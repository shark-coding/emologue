package com.project.emologue.model.admin;

import jakarta.validation.constraints.NotEmpty;

public record AdminLoginRequestBody(
        @NotEmpty String username,
        @NotEmpty String password) {}
