package com.project.emologue.model.job;

import jakarta.validation.constraints.NotEmpty;

public record JobRequestBody(@NotEmpty String jobname, @NotEmpty String description) {
}
