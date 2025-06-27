package com.project.emologue.repository;

import com.project.emologue.model.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {
    Optional<JobEntity> findByJobname(String jobname);
    Optional<JobEntity> findByJobId(Long jobId);
}
