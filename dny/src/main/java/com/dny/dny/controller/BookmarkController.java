package com.dny.dny.controller;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import com.dny.dny.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 추가/삭제 토글
    @PostMapping("/bookmark/{jobId}")
    public String toggleBookmark(@PathVariable String jobId) {
        bookmarkService.toggleBookmark(jobId);
        return "북마크 토글 완료";
    }

    // 북마크 목록 조회
    @GetMapping("/bookmark")
    public List<Bookmark> getBookmarks() {
        return bookmarkService.getBookmarks();
    }

    // 강제 삭제 (관리자용..일단 써둠)
    @DeleteMapping("/bookmark/{jobId}")
    public void deleteBookmark(@PathVariable String jobId) {
        bookmarkService.deleteBookmark(jobId);
    }
}

