package com.project.emologue.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(
        @NotEmpty
        @Schema(description = "사용자 아이디", example = "sanga")
        String username,

        @NotEmpty
        @Schema(description = "사용자 비밀번호", example = "1234")
        String password,

        @NotEmpty
        @Schema(description = "사용자 이름", example = "KimSanga")
        String name,

        @NotEmpty
        @Schema(description = "사용자 직업", example = "backend")
        String jobname) {}
