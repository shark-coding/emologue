package com.project.emologue.service;

import com.project.emologue.exception.job.JobAlreadyExistsException;
import com.project.emologue.exception.job.JobNotFoundException;
import com.project.emologue.model.entity.JobEntity;
import com.project.emologue.model.job.Job;
import com.project.emologue.model.job.JobPatchRequestBody;
import com.project.emologue.model.job.JobPostRequestBody;
import com.project.emologue.repository.JobEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    @Mock private JobEntityRepository jobEntityRepository;

    @InjectMocks private JobService jobService;

    private JobPostRequestBody postRequest;
    private JobPatchRequestBody patchRequest;
    private JobEntity jobEntity;

    @BeforeEach
    void setUp() {
        jobEntity = JobEntity.of("backend", "백엔드 엔지니어");
        postRequest = new JobPostRequestBody("backend", "백엔드 엔지니어");
        patchRequest = new JobPatchRequestBody("frontend", "프론트엔드 엔지니어");
    }

    @Test
    void 직업등록_성공() {
        when(jobEntityRepository.existsByJobname("backend")).thenReturn(false);
        when(jobEntityRepository.save(any(JobEntity.class))).thenReturn(jobEntity);

        Job job = jobService.createJob(postRequest);
        assertEquals("backend", job.jobname());
        assertEquals("백엔드 엔지니어", job.description());
    }

    @Test
    void 직업등록_중복이면_예외() {
        when(jobEntityRepository.existsByJobname("backend")).thenReturn(true);

        assertThrows(JobAlreadyExistsException.class, () -> {
            jobService.createJob(postRequest);
        });
    }

    @Test
    void 작업수정_성공() {
        Long jobId = 1L;
        when(jobEntityRepository.findByJobId(jobId)).thenReturn(Optional.of(jobEntity));
        when(jobEntityRepository.save(any(JobEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Job updatedJob = jobService.updateJob(jobId, patchRequest);

        assertEquals("frontend", updatedJob.jobname());
        assertEquals("프론트엔드 엔지니어", updatedJob.description());
    }

    @Test
    void 직업수정_존재하지않으면_예외발생() {
        Long jobId = 1L;
        when(jobEntityRepository.findByJobId(jobId)).thenReturn(Optional.empty());
        assertThrows(JobNotFoundException.class, () -> {
            jobService.updateJob(jobId,patchRequest);
        });
    }

    @Test
    void 직업삭제_성공() {
        Long jobId = 1L;
        when(jobEntityRepository.findByJobId(jobId)).thenReturn(Optional.of(jobEntity));

        jobService.deleteJob(jobId);

        verify(jobEntityRepository, times(1)).delete(jobEntity);
    }

    @Test
    void 직업삭제_존재하지않으면_예외발생() {
        Long jobId = 1L;
        when(jobEntityRepository.findByJobId(jobId)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> {
            jobService.deleteJob(jobId);
        });
    }

    @Test
    void 전체_직업조회_성공() {
        List<JobEntity> jobEntities = List.of(
                JobEntity.of("backend", "백엔드 엔지니어"),
                JobEntity.of("frontend", "프론트엔드 엔지니어")
        );

        when(jobEntityRepository.findAll()).thenReturn(jobEntities);

        List<Job> jobs = jobService.getJobs();

        assertEquals(2, jobs.size());
        assertEquals("frontend", jobs.get(1).jobname());
    }
}
