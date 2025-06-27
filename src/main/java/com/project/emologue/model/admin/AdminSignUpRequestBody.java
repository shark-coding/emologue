package com.project.emologue.model.admin;

import jakarta.validation.constraints.NotEmpty;

public record AdminSignUpRequestBody(
        @NotEmpty String username,
        @NotEmpty String password) {}
