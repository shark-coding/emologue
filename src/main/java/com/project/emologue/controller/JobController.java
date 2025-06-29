package com.project.emologue.controller;

import com.project.emologue.model.job.Job;
import com.project.emologue.model.job.JobPatchRequestBody;
import com.project.emologue.model.job.JobPostRequestBody;
import com.project.emologue.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired private JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getJobs() {
        List<Job> jobs = jobService.getJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Job> getJobByJobId(@PathVariable long jobId) {
        Job job = jobService.getJobByJobId(jobId);
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody JobPostRequestBody createJob) {
        Job job = jobService.createJob(createJob);
        return ResponseEntity.ok(job);
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable long jobId, @Valid @RequestBody JobPatchRequestBody updateJob) {
        Job job = jobService.updateJob(jobId, updateJob);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
