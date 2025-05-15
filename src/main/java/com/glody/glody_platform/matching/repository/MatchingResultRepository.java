package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {
    List<MatchingResult> findBySessionId(Long sessionId);
}