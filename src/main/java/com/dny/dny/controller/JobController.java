package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
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

    /* 외부 API 직접 확인용 (테스트 전용) */
    @GetMapping("/it")
    public ApiResponse<List<JobDto>> getItJobs() {
        return ApiResponse.success(jobService.getItJobs());
    }

    /* DB 기준 공고 목록 조회 (북마크 포함) */
    @GetMapping
    public ApiResponse<List<JobResponseDto>> getJobs(HttpSession session) {
        Long userId = getLoginUserId(session);
        return ApiResponse.success(jobService.getJobsWithBookmark(userId));
    }

    /* 제목 검색 */
    @GetMapping("/search")
    public ApiResponse<List<Job>> search(@RequestParam String keyword,
                            HttpSession session) {

        getLoginUserId(session);
        return ApiResponse.success(jobService.searchJobs(keyword));
    }

    /* 지역 필터 */
    @GetMapping("/location")
    public ApiResponse<List<Job>> filterByLocation(@RequestParam String location,
                                      HttpSession session) {

        getLoginUserId(session);
        return ApiResponse.success(jobService.filterByLocation(location));
    }

    /* 채용구분 필터 */
    @GetMapping("/type")
    public ApiResponse<List<Job>> filterByType(@RequestParam String jobType,
                                  HttpSession session) {

        getLoginUserId(session);
        return ApiResponse.success(jobService.filterByJobType(jobType));
    }

    /* 마감 임박순 정렬 */
    @GetMapping("/deadline")
    public ApiResponse<List<Job>> sortByDeadline(HttpSession session) {

        getLoginUserId(session);
        return ApiResponse.success(jobService.sortByDeadline());
    }

    /* 최신순 정렬 */
    @GetMapping("/latest")
    public ApiResponse<List<Job>> sortByLatest(HttpSession session) {

        getLoginUserId(session);
        return ApiResponse.success(jobService.sortByLatest());
    }

    /* 로그인 사용자 확인 */
    private Long getLoginUserId(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return userId;
    }
}
