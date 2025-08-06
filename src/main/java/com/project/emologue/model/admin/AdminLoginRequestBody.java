package com.project.emologue.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record AdminLoginRequestBody(
        @NotEmpty
        @Schema(description = "관리자 아이디", example = "admin")
        String username,

        @NotEmpty
        @Schema(description = "관리자 비밀번호", example = "1234")
        String password) {}
