package com.project.emologue.model.job;

import io.swagger.v3.oas.annotations.media.Schema;

public record JobPatchRequestBody(
        @Schema(description = "직업 이름", example = "backend")
        String jobname,

        @Schema(description = "직업 설명", example = "백엔드 엔지니어")
        String description) {
}
