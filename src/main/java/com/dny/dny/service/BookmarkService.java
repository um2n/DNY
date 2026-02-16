package com.dny.dny.service;

import com.dny.dny.entity.Bookmark;
import com.dny.dny.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    // 북마크 추가/취소 토글
    @Transactional
    public String toggleBookmark(String jobId, Long userId) {

        if (bookmarkRepository.existsByUserIdAndJobId(userId, jobId)) {
            bookmarkRepository.deleteByUserIdAndJobId(userId, jobId);
            return "북마크 취소";
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(userId);
        bookmark.setJobId(jobId);
        bookmarkRepository.save(bookmark);

        return "북마크 완료";
    }

    // 특정 사용자의 북마크 목록 조회
    public List<Bookmark> getBookmarks(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    // 특정 사용자의 북마크된 jobId 집합 반환
    public Set<String> getBookmarkedJobIds(Long userId) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(Bookmark::getJobId)
                .collect(Collectors.toSet());
    }

    // 특정 공고 북마크 삭제
    @Transactional
    public void deleteBookmark(String jobId, Long userId) {
        bookmarkRepository.deleteByUserIdAndJobId(userId, jobId);
    }
}
