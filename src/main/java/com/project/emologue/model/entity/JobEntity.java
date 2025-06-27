package com.project.emologue.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(nullable = false)
    private String jobname;

    @Column(nullable = false)
    private String description;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEntity jobEntity = (JobEntity) o;
        return Objects.equals(jobId, jobEntity.jobId) && Objects.equals(jobname, jobEntity.jobname) && Objects.equals(description, jobEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobname, description);
    }

    public static JobEntity of(String jobname, String description) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setJobname(jobname);
        jobEntity.setDescription(description);
        return jobEntity;
    }
}
