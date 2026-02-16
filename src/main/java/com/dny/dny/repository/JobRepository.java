package com.dny.dny.repository;

import com.dny.dny.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JobRepository
        extends JpaRepository<Job, String>,
        JpaSpecificationExecutor<Job> {

    @Query("select j.jobId from Job j")
    List<String> findAllJobIds();

    void deleteByDeadlineBefore(LocalDate today);
}
