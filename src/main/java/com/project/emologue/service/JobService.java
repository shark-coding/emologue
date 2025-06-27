package com.project.emologue.service;

import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.job.Job;
import com.project.emologue.model.job.JobRequestBody;
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

    public JobEntity getJobEntityByJobId(Long jobId) {
        return jobEntityRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));
    }

    public Job createJob(JobRequestBody createJob) {
        JobEntity jobEntity = JobEntity.of(createJob.jobname());
        return Job.from(jobEntityRepository.save(jobEntity));
    }

    public Job updateJob(Long jobId, JobRequestBody updateJob) {
        JobEntity jobEntity = getJobEntityByJobId(jobId);
        if (!ObjectUtils.isEmpty(updateJob.jobname())) {
            jobEntity.setJobname(updateJob.jobname());
        }
        return Job.from(jobEntityRepository.save(jobEntity));
    }

    public void deleteJob(long jobId) {
        JobEntity jobEntity = getJobEntityByJobId(jobId);
        jobEntityRepository.delete(jobEntity);
    }
}
