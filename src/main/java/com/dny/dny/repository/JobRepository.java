package com.dny.dny.repository;

import com.dny.dny.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * DB 데이터 접근 인터페이스 (동적 쿼리 및 페이징 지원)
 */
public interface JobRepository extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {

    /* 중복 체크용 공고 고유 ID 리스트 조회 */
    @Query("select j.jobId from Job j")
    List<String> findAllJobIds();

    /* 마감된 공고 일괄 삭제 */
    @org.springframework.data.jpa.repository.Modifying
    void deleteByDeadlineBefore(LocalDate today);
}
