package com.dny.dny.service;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final Long TEST_USER_ID = 1L;

    public void addBookmark(String jobId) {
        Bookmark b = new Bookmark();
        b.setUserId(TEST_USER_ID);
        b.setJobId(jobId);
        bookmarkRepository.save(b);
    }

    public List<Bookmark> getBookmarks() {
        return bookmarkRepository.findByUserId(TEST_USER_ID);
    }
}
