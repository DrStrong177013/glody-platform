package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingScoreRepository extends JpaRepository<MatchingScore, Long> {
    List<MatchingScore> findByResultId(Long resultId);
}