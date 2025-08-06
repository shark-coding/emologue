package com.project.emologue.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(
        @NotEmpty
        @Schema(description = "사용자 아이디", example = "sanga")
        String username,

        @NotEmpty
        @Schema(description = "사용자 비밀번호", example = "1234")
        String password) {}
