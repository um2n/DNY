package com.dny.dny.controller;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import com.dny.dny.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 추가/삭제 토글
    @PostMapping("/bookmark/{jobId}")
    public String toggleBookmark(@PathVariable String jobId,
                                 HttpSession session) {


        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return bookmarkService.toggleBookmark(jobId, userId);


    }

    // 북마크 목록 조회
    @GetMapping("/bookmark")
    public List<Bookmark> getBookmarks(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return bookmarkService.getBookmarks(userId);
    }

    // 삭제
    @DeleteMapping("/bookmark/{jobId}")
    public void deleteBookmark(@PathVariable String jobId,
                               HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        bookmarkService.deleteBookmark(jobId, userId);
    }
}


