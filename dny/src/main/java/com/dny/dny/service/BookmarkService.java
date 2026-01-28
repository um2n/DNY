package com.dny.dny.service;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final Long TEST_USER_ID = 1L;

    public void toggleBookmark(String jobId) {

        Optional<Bookmark> existing =
                bookmarkRepository.findByUserIdAndJobId(TEST_USER_ID, jobId);

        if (existing.isPresent()) {
            // 이미 있으면 삭제(중복 방지)
            bookmarkRepository.delete(existing.get()); // 삭제
            System.out.println("북마크 삭제됨");
            return;
        }

        // 없으면 추가
        Bookmark b = new Bookmark();
        b.setUserId(TEST_USER_ID);
        b.setJobId(jobId);
        bookmarkRepository.save(b);
        System.out.println("북마크 저장됨");

    }

    public void deleteBookmark(String jobId) {
        bookmarkRepository.deleteByUserIdAndJobId(TEST_USER_ID, jobId);
    }

    public List<Bookmark> getBookmarks() {
        return bookmarkRepository.findByUserId(TEST_USER_ID);
    }


}
