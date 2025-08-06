package com.project.emologue.controller;

import com.project.emologue.model.error.ErrorResponse;
import com.project.emologue.model.job.Job;
import com.project.emologue.model.job.JobPatchRequestBody;
import com.project.emologue.model.job.JobPostRequestBody;
import com.project.emologue.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Job API", description = "직업 API")
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired private JobService jobService;

    @GetMapping("/user")
    @Operation(summary = "직업 조회", description = "직업 다건 조회")
    @ApiResponse(
            responseCode = "200", description = "직업 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Job.class),
                    examples = @ExampleObject(value = "[\n" +
                            " {\"jobId\":\"1\", \"jobname\":\"backend\", \"description\":\"백엔드 엔지니어\"}, \n" +
                            " {\"jobId\":\"2\", \"jobname\":\"frontend\", \"description\":\"프론트엔드 엔지니어\"}\n" +
                            "]")
            )
    )
    public ResponseEntity<List<Job>> getJobs() {
        List<Job> jobs = jobService.getJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/admin/{jobId}")
    @Operation(summary = "직업 조회", description = "직업 단건 조회")
    @ApiResponse(
            responseCode = "200", description = "직업 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Job.class),
                    examples = @ExampleObject(value = "{\"jobId\":\"1\", \"jobname\":\"backend\", \"description\":\"백엔드 엔지니어\"}")
            )
    )
    public ResponseEntity<Job> getJobByJobId(
            @Parameter(description = "조회할 jobId", required = true, example = "1")
            @PathVariable long jobId) {
        Job job = jobService.getJobByJobId(jobId);
        return ResponseEntity.ok(job);
    }

    @PostMapping("/admin")
    @Operation(summary = "직업 등록", description = "관리자 직업 신규 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "직업 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JobPostRequestBody.class),
                            examples = @ExampleObject(value = "{\"jobId\":\"1\", \"jobname\":\"backend\", \"description\": \"백엔드 엔지니어\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"Job with jobname backend already exists\"}")
                    ))
    })
    public ResponseEntity<Job> createJob(
            @Parameter(description = "직업 등록 요청 데이터", required = true)
            @Valid @RequestBody JobPostRequestBody createJob) {
        Job job = jobService.createJob(createJob);
        return ResponseEntity.ok(job);
    }

    @PatchMapping("/admin/{jobId}")
    @Operation(summary = "직업 수정", description = "관리자 직업 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "직업 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JobPatchRequestBody.class),
                            examples = @ExampleObject(value = "{\"jobId\":\"1\", \"jobname\":\"backend\", \"description\": \"백엔드 엔지니어\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\", \"message\":\"Required request body is missing\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONFLICT\", \"message\":\"Job with jobname backend already exists\"}")
                    ))
    })
    public ResponseEntity<Job> updateJob(
            @Parameter(description = "조회할 jobId", required = true, example = "1")
            @PathVariable long jobId,
            @Parameter(description = "수정할 내용", required = true)
            @Valid @RequestBody JobPatchRequestBody updateJob) {
        Job job = jobService.updateJob(jobId, updateJob);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/admin/{jobId}")
    @Operation(summary = "직업 삭제", description = "관리자 직업 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "직업 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\", \"message\":\"Job with jobId 100 not found\"}")
                    ))
    })
    public ResponseEntity<Void> deleteJob(
            @Parameter(description = "삭제할 jobId", required = true, example = "100")
            @PathVariable long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
