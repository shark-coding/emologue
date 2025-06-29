package com.project.emologue.model.job;

import jakarta.validation.constraints.NotEmpty;

public record JobPatchRequestBody(String jobname, String description) {
}
