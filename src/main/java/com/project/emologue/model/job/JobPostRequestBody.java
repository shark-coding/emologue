package com.project.emologue.model.job;

import jakarta.validation.constraints.NotEmpty;

public record JobPostRequestBody(@NotEmpty String jobname, @NotEmpty String description) {
}
