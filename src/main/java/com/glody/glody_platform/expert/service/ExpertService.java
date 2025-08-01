package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.expert.repository.ExpertProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private static final String ROLE_EXPERT = "EXPERT";

    private final ExpertProfileRepository expertRepo;

    /**
     * Trả về trang experts (chỉ những users có role EXPERT),
     * nếu keyword null/blank thì load toàn bộ, ngược lại search.
     */
    public Page<ExpertProfileDto> getExperts(String keyword, Pageable pageable) {
        Page<ExpertProfile> page;
        if (keyword == null || keyword.isBlank()) {
            page = expertRepo.findAllByUser_Roles_RoleName(ROLE_EXPERT, pageable);
        } else {
            page = expertRepo.searchByKeyword(ROLE_EXPERT, keyword.trim(), pageable);
        }
        return page.map(this::mapToDto);
    }

    /**
     * Chuyển ExpertProfile → ExpertProfileDto bằng setter (không dùng builder)
     */
    private ExpertProfileDto mapToDto(ExpertProfile e) {
        ExpertProfileDto dto = new ExpertProfileDto();
        dto.setUserId(e.getUser().getId());
        dto.setFullName(e.getUser().getFullName());
        dto.setEmail(e.getUser().getEmail());
        dto.setAvatarUrl(e.getAvatarUrl());
        dto.setBio(e.getBio());
        dto.setExpertise(e.getExpertise());
        dto.setExperience(e.getExperience());
        dto.setYearsOfExperience(e.getYearsOfExperience());
        // Đổ danh sách tên quốc gia
//        List<String> countries = e.getAdvisingCountries()
//                .stream()
//                .map(Country::getName)
//                .toList();
//        dto.setCountryNames(countries);
        return dto;
    }
}
