package com.dny.dny.repository;

import com.dny.dny.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface JobRepository
        extends JpaRepository<Job, String>,
        JpaSpecificationExecutor<Job> {

    // 제목 검색
    List<Job> findByTitleContaining(String keyword);

    // 지역 필터
    List<Job> findByLocation(String location);

    // 채용구분 필터 (인턴/신입공채 등)
    List<Job> findByJobType(String jobType);

    // 마감일 임박순 정렬
    List<Job> findAllByOrderByDeadlineAsc();

    // 최신 등록순 정렬
    List<Job> findAllByOrderByCreatedAtDesc();
}
