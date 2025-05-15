package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findTopByUserIdAndIsActiveTrueOrderByEndDateDesc(Long userId);
    List<UserSubscription> findAllByUserIdAndIsActiveTrue(Long userId);
}