package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.entity.Bookmark;
import com.dny.dny.service.BookmarkService;
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

    /* 북마크 토글 (추가 / 취소) */
    @PostMapping("/{jobId}")
    public ApiResponse<String> toggleBookmark(@PathVariable String jobId,
                                 HttpSession session) {

        Long userId = getLoginUserId(session);
        String result = bookmarkService.toggleBookmark(jobId, userId);
        return ApiResponse.success(result, null);
    }

    /* 북마크 목록 조회 */
    @GetMapping
    public ApiResponse<List<Bookmark>> getBookmarks(HttpSession session) {

        Long userId = getLoginUserId(session);
        return ApiResponse.success(bookmarkService.getBookmarks(userId));
    }

    /* 북마크 삭제 */
    @DeleteMapping("/{jobId}")
    public ApiResponse<Void> deleteBookmark(@PathVariable String jobId,
                               HttpSession session) {

        Long userId = getLoginUserId(session);
        bookmarkService.deleteBookmark(jobId, userId);
        return ApiResponse.success("북마크가 삭제되었습니다.", null);
    }

    /* 세션에서 로그인 사용자 ID 조회 */
    private Long getLoginUserId(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return userId;
    }
}
