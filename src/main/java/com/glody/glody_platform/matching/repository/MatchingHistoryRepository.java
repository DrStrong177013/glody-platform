package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingHistory;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.users.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {

    List<MatchingHistory> findByUserIdAndMatchTypeOrderByCreatedAtDesc(Long userId, String matchType);
    List<MatchingHistory> findByUserId(Long userId); // để tìm User từ userId
    List<MatchingHistory> findByUserIdAndMatchType(Long userId,String matchType);



}