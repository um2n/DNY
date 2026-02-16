package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.service.JobService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
public class JobController {

    private final JobService jobService;

    /**
     * 통합 공고 목록 조회
     * keyword(검색어), location(지역), jobType(직무) 필터 조합 및 페이징 처리
     */
    @GetMapping
    public ApiResponse<Page<JobResponseDto>> getJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            HttpSession session, 
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        
        return ApiResponse.success(jobService.getJobs(keyword, location, jobType, getLoginUserId(session), pageable));
    }

    /**
     * 데이터 수집 확인용 API (외부 API 원본 데이터 반환)
     */
    @GetMapping("/it")
    public ApiResponse<List<JobDto>> getItJobs() {
        return ApiResponse.success(jobService.getItJobs());
    }

    /**
     * 세션 기반 로그인 사용자 ID 조회 (미로그인 시 런타임 예외 발생)
     */
    private Long getLoginUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new RuntimeException("로그인이 필요합니다.");
        return userId;
    }
}
