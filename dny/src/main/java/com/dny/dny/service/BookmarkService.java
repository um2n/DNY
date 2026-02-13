package com.dny.dny.service;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.dny.dny.entity.User;
import com.dny.dny.repository.UserRepository;




@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public String toggleBookmark(String jobId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Optional<Bookmark> existing =
                bookmarkRepository.findByUserIdAndJobId(userId, jobId);

        if (existing.isPresent()) {
            bookmarkRepository.delete(existing.get());
            return "북마크 삭제 완료";
        }

        Bookmark b = new Bookmark();
        b.setUserId(userId);   // ✅ TEST_USER_ID 말고 userId
        b.setJobId(jobId);
        bookmarkRepository.save(b);

        return "북마크 저장 완료";
    }


    public void deleteBookmark(String jobId, Long userId) {
        bookmarkRepository.deleteByUserIdAndJobId(userId, jobId);
    }

    public List<Bookmark> getBookmarks(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }


}
