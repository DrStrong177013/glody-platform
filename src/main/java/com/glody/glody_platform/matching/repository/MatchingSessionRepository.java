package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingSessionRepository extends JpaRepository<MatchingSession, Long> {
    List<MatchingSession> findByUserId(Long userId);
}