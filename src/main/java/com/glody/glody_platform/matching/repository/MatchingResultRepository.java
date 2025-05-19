package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingResult;
import com.glody.glody_platform.matching.entity.MatchingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {
    List<MatchingResult> findBySession(MatchingSession session);
}
