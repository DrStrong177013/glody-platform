package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingResult;
import com.glody.glody_platform.matching.entity.MatchingStatusLog;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingStatusLogRepository extends JpaRepository<MatchingStatusLog, Long> {
    List<MatchingStatusLog> findAllByUserAndResultOrderByCreatedAtDesc(User user, MatchingResult result);
}
