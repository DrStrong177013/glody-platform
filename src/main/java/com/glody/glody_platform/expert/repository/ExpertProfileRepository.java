package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {
    Optional<ExpertProfile> findByUser(User user);
}
