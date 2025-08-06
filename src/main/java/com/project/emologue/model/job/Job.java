package com.project.emologue.model.job;

import com.project.emologue.model.entity.JobEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record Job(
        @Schema(description = "직업 PK", example = "1")
        Long jobId,
        @Schema(description = "직업 이름", example = "backend")
        String jobname,
        @Schema(description = "직업 설명", example = "백엔드 엔지니어")
        String description) {

    public static Job from(JobEntity jobEntity) {
        return new Job(
                jobEntity.getJobId(),
                jobEntity.getJobname(),
                jobEntity.getDescription());
    }
}
