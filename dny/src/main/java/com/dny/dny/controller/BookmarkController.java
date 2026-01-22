package com.dny.dny.controller;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;

    // 임시 로그인 사용자 (지금 단계는 하드코딩)
    private final Long TEST_USER_ID = 1L;

    // ① 스크랩 추가
    @PostMapping("/bookmark/{jobId}")
    public String addBookmark(@PathVariable String jobId) {
        Bookmark b = new Bookmark();
        b.setUserId(TEST_USER_ID);
        b.setJobId(jobId);
        bookmarkRepository.save(b);
        return "스크랩 저장 완료";
    }

    // ② 스크랩 조회
    @GetMapping("/bookmark")
    public List<Bookmark> getBookmarks() {
        return bookmarkRepository.findByUserId(TEST_USER_ID);
    }
}
