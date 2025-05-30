package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {

    List<MatchingHistory> findByUserIdAndMatchTypeOrderByCreatedAtDesc(Long userId, String matchType);
}