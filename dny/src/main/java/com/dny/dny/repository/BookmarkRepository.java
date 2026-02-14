package com.dny.dny.repository;

import com.dny.dny.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndJobId(Long userId, String jobId);
    boolean existsByUserIdAndJobId(Long userId, String jobId);
    List<Bookmark> findByUserId(Long userID);

    void deleteByUserIdAndJobId(Long userId, String jobId);

}

