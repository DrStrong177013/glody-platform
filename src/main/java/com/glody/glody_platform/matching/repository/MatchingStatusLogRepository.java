package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingStatusLogRepository extends JpaRepository<MatchingStatusLog, Long> {
    Optional<MatchingStatusLog> findByResultId(Long resultId);
}
