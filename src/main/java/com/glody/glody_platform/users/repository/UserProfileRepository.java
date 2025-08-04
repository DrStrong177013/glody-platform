package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
    @Query("SELECT new com.glody.glody_platform.users.dto.UserProfileDto(u) FROM UserProfile u WHERE u.user.id = :userId")
    UserProfileDto findDtoByUserId(Long userId);
}