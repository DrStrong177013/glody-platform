package com.glody.glody_platform.matching.repository;

import com.glody.glody_platform.matching.entity.MatchingResult;
import com.glody.glody_platform.matching.entity.MatchingStatus;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MatchingStatusRepository extends JpaRepository<MatchingStatus, Long> {
    Optional<MatchingStatus> findByUserAndResult(User user, MatchingResult result);
    List<MatchingStatus> findAllByUser(User user);
}
