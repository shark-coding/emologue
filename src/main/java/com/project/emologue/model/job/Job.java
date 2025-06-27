package com.project.emologue.model.job;

import com.project.emologue.model.entity.JobEntity;

public record Job(Long jobId, String jobname, String description) {

    public static Job from(JobEntity jobEntity) {
        return new Job(
                jobEntity.getJobId(),
                jobEntity.getJobname(),
                jobEntity.getDescription());
    }
}
