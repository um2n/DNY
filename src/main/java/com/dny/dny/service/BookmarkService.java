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

    /**
     * 북마크 상태 토글 (존재하면 삭제, 없으면 추가)
     */
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

    /**
     * 사용자별 북마크 리스트 조회
     */
    public List<Bookmark> getBookmarks(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    /**
     * 사용자별 북마크된 공고 ID 세트 조회 (조회 시 북마크 여부 체크용)
     */
    public Set<String> getBookmarkedJobIds(Long userId) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(Bookmark::getJobId)
                .collect(Collectors.toSet());
    }

    /**
     * 특정 공고 북마크 삭제
     */
    @Transactional
    public void deleteBookmark(String jobId, Long userId) {
        bookmarkRepository.deleteByUserIdAndJobId(userId, jobId);
    }
}
