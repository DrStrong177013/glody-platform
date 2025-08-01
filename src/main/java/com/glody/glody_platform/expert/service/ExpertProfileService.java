// ExpertProfileService.java
package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.expert.repository.ExpertProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertProfileService {

    private final ExpertProfileRepository expertRepo;
    private final CountryRepository countryRepo;

    /**
     * Lấy profile của expert theo userId.
     * @throws 404 nếu không tìm thấy expert_profiles.user_id = userId
     */
    @Transactional(readOnly = true)
    public ExpertProfileDto getExpertProfileByUserId(Long userId) {
        ExpertProfile profile = expertRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expert profile not found for userId=" + userId));

        return toDto(profile);
    }

    /**
     * Cập nhật thông tin chuyên gia và user.fullName.
     * Nếu dto.fullName không null thì update tên trên User.
     */
    @Transactional
    public void updateExpertProfile(Long userId, ExpertProfileUpdateDto dto) {
        ExpertProfile profile = expertRepo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expert profile not found for userId=" + userId));

        // --- 1) Update User.fullName nếu có ---
        if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
            profile.getUser().setFullName(dto.getFullName());
        }

        // --- 2) Update avatarUrl nếu có ---
        if (dto.getAvatarUrl() != null) {
            profile.setAvatarUrl(dto.getAvatarUrl());
        }

        // --- 3) Các trường khác ---
        profile.setBio(dto.getBio());
        profile.setExpertise(dto.getExpertise());
        profile.setExperience(dto.getExperience());
        profile.setYearsOfExperience(dto.getYearsOfExperience());

        // --- 4) Update advisingCountries nếu client truyền lên ---
//        if (dto.getCountryIds() != null) {
//            List<Country> countries = countryRepo.findAllById(dto.getCountryIds());
//            profile.setAdvisingCountries(new HashSet<>(countries));
//        }

        // @Transactional sẽ tự động flush cả profile và user
    }

    /**
     * Chuyển ExpertProfile entity → ExpertProfileDto
     */
    private ExpertProfileDto toDto(ExpertProfile profile) {
        ExpertProfileDto dto = new ExpertProfileDto();
        dto.setUserId(profile.getUser().getId());
        dto.setFullName(profile.getUser().getFullName());
        dto.setEmail(profile.getUser().getEmail());
        dto.setAvatarUrl(profile.getAvatarUrl());
        dto.setBio(profile.getBio());
        dto.setExpertise(profile.getExpertise());
        dto.setExperience(profile.getExperience());
        dto.setYearsOfExperience(profile.getYearsOfExperience());
//        dto.setCountryNames(
//                profile.getAdvisingCountries().stream()
//                        .map(Country::getName)
//                        .toList()
//        );
        return dto;
    }
}
