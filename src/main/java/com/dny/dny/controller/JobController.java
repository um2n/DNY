package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.service.JobService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
public class JobController {

    private final JobService jobService;

    /* 통합 공고 목록 조회 (필터링 및 검색 포함) */
    @GetMapping
    public ApiResponse<Page<JobResponseDto>> getJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            HttpSession session, 
            @org.springframework.data.web.PageableDefault(sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        
        Long userId = getLoginUserId(session);
        return ApiResponse.success(jobService.getJobs(keyword, location, jobType, userId, pageable));
    }

    /* 외부 API 직접 확인용 (테스트용) */
    @GetMapping("/it")
    public ApiResponse<List<JobDto>> getItJobs() {
        return ApiResponse.success(jobService.getItJobs());
    }

    private Long getLoginUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new RuntimeException("로그인이 필요합니다.");
        return userId;
    }
}
