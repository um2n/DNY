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

    @PostMapping("/bookmark/{jobId}")
    public String addBookmark(@PathVariable String jobId) {
        bookmarkService.addBookmark(jobId);
        return "스크랩 저장 완료";
    }

    @GetMapping("/bookmark")
    public List<Bookmark> getBookmarks() {
        return bookmarkService.getBookmarks();
    }
}

