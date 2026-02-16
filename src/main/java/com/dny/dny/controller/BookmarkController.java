package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.entity.Bookmark;
import com.dny.dny.service.BookmarkService;
import com.dny.dny.service.JobService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final JobService jobService;

    /**
     * 스크랩한 공고 상세 목록 조회 (마이페이지용)
     */
    @GetMapping("/my")
    public ApiResponse<List<JobResponseDto>> getMyBookmarks(HttpSession session) {
        return ApiResponse.success(jobService.getMyBookmarkedJobs(getLoginUserId(session)));
    }

    /**
     * 북마크 토글 (추가 또는 취소)
     */
    @PostMapping("/{jobId}")
    public ApiResponse<String> toggleBookmark(@PathVariable String jobId, HttpSession session) {
        return ApiResponse.success(bookmarkService.toggleBookmark(jobId, getLoginUserId(session)), null);
    }

    /**
     * 특정 사용자의 전체 북마크 엔티티 조회
     */
    @GetMapping
    public ApiResponse<List<Bookmark>> getBookmarks(HttpSession session) {
        return ApiResponse.success(bookmarkService.getBookmarks(getLoginUserId(session)));
    }

    /**
     * 북마크 삭제
     */
    @DeleteMapping("/{jobId}")
    public ApiResponse<Void> deleteBookmark(@PathVariable String jobId, HttpSession session) {
        bookmarkService.deleteBookmark(jobId, getLoginUserId(session));
        return ApiResponse.success("북마크 삭제 완료", null);
    }

    /* 세션 기반 로그인 사용자 ID 조회 */
    private Long getLoginUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new RuntimeException("로그인이 필요합니다.");
        return userId;
    }
}
