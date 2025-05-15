package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileDto getProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserProfileDto dto = new UserProfileDto();
        dto.setNationality(profile.getNationality());
        dto.setEducationLevel(profile.getEducationLevel());
        dto.setMajor(profile.getMajor());
        dto.setTargetCountry(profile.getTargetCountry());
        dto.setTargetYear(profile.getTargetYear());
        dto.setGpa(profile.getGpa());
        dto.setLanguageCertificate(profile.getLanguageCertificate());
        return dto;
    }

    @Transactional
    public void saveOrUpdate(Long userId, UserProfileDto dto) {
        User user = userRepository.findById(userId.toString()).orElseThrow();
        UserProfile profile = userProfileRepository.findByUserId(userId).orElse(new UserProfile());
        profile.setUser(user);
        profile.setNationality(dto.getNationality());
        profile.setEducationLevel(dto.getEducationLevel());
        profile.setMajor(dto.getMajor());
        profile.setTargetCountry(dto.getTargetCountry());
        profile.setTargetYear(dto.getTargetYear());
        profile.setGpa(dto.getGpa());
        profile.setLanguageCertificate(dto.getLanguageCertificate());
        userProfileRepository.save(profile);
    }
}