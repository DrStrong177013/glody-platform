package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.expert.repository.ExpertProfileRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertProfileService {

    private final ExpertProfileRepository expertRepo;
    private final UserRepository userRepo;
    private final CountryRepository countryRepo;

    @Transactional
    public void updateExpertProfile(Long userId, ExpertProfileUpdateDto dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ExpertProfile profile = expertRepo.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert profile not found"));

        // Cập nhật user cơ bản
        user.setFullName(dto.getFullName());
        profile.setAvatarUrl(dto.getAvatarUrl());
        // Cập nhật expert profile
        profile.setBio(dto.getBio());
        profile.setExpertise(dto.getExpertise());
        profile.setExperience(dto.getExperience());
        profile.setYearsOfExperience(dto.getYearsOfExperience());

        // Cập nhật danh sách quốc gia tư vấn
        if (dto.getCountryIds() != null) {
            List<Country> countries = countryRepo.findAllById(dto.getCountryIds());
            profile.setAdvisingCountries(new HashSet<>(countries));
        }

        expertRepo.save(profile);
    }

    public ExpertProfileDto getExpertProfileByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ExpertProfile expertProfile = expertRepo.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert profile not found"));

        return mapToDto(expertProfile);
    }

    private ExpertProfileDto mapToDto(ExpertProfile profile) {
        User user = profile.getUser();

        ExpertProfileDto dto = new ExpertProfileDto();
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatarUrl(profile.getAvatarUrl());
        dto.setEmail(user.getEmail());

        dto.setBio(profile.getBio());
        dto.setExpertise(profile.getExpertise());
        dto.setExperience(profile.getExperience());
        dto.setYearsOfExperience(profile.getYearsOfExperience());

        dto.setCountryNames(
                profile.getAdvisingCountries().stream().map(Country::getName).toList()
        );

        return dto;
    }

}
