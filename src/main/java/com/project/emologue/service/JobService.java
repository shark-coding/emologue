package com.project.emologue.service;

import com.project.emologue.exception.job.JobAlreadyExistsException;
import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.job.Job;
import com.project.emologue.model.job.JobPatchRequestBody;
import com.project.emologue.model.job.JobPostRequestBody;
import com.project.emologue.repository.JobEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class JobService {
    @Autowired private JobEntityRepository jobEntityRepository;

    public List<Job> getJobs() {
        List<JobEntity> jobEntities = jobEntityRepository.findAll();
        return jobEntities.stream().map(Job::from).toList();
    }

    public Job getJobByJobId(long jobId) {
        JobEntity jobEntity = getJobEntityByJobId(jobId);
        return Job.from(jobEntity);
    }

    public JobEntity getJobEntityByJobId(long jobId) {
        return jobEntityRepository.findByJobId(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));
    }

    public Job createJob(JobPostRequestBody createJob) {
        if (jobEntityRepository.existsByJobname(createJob.jobname())) {
            throw new JobAlreadyExistsException(createJob.jobname());
        }
        var jobEntity = JobEntity.of(createJob.jobname(), createJob.description());
        return Job.from(jobEntityRepository.save(jobEntity));
    }

    public Job updateJob(Long jobId, JobPatchRequestBody updateJob) {
        var jobEntity = getJobEntityByJobId(jobId);
        if (!ObjectUtils.isEmpty(updateJob.jobname())) {
            jobEntity.setJobname(updateJob.jobname());
        }
        if (!ObjectUtils.isEmpty(updateJob.description())) {
            jobEntity.setDescription(updateJob.description());
        }
        return Job.from(jobEntityRepository.save(jobEntity));
    }

    public void deleteJob(long jobId) {
        var jobEntity = getJobEntityByJobId(jobId);
        jobEntityRepository.delete(jobEntity);
    }
}
