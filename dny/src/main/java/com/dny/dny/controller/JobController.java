package com.dny.dny.controller;

import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.entity.Job;
import com.dny.dny.service.JobService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
public class JobController {

    private final JobService jobService;

    // 🔹 API 테스트용 (유지해도 됨)
    @GetMapping("/it")
    public List<JobDto> getItJobs() {
        return jobService.getItJobs();
    }

    // 🔥 실제 목록 (DB 기준 + 북마크 포함)
    @GetMapping
    public List<JobResponseDto> getJobs(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return jobService.getJobsWithBookmark(userId);
    }


    // 검색
    @GetMapping("/search")
    public List<Job> search(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }

    // 지역 필터
    @GetMapping("/location")
    public List<Job> filterByLocation(@RequestParam String location) {
        return jobService.filterByLocation(location);
    }

    // 채용구분 필터
    @GetMapping("/type")
    public List<Job> filterByType(@RequestParam String jobType) {
        return jobService.filterByJobType(jobType);
    }

    // 마감임박순
    @GetMapping("/deadline")
    public List<Job> sortByDeadline() {
        return jobService.sortByDeadline();
    }

    // 최신순
    @GetMapping("/latest")
    public List<Job> sortByLatest() {
        return jobService.sortByLatest();
    }
}
