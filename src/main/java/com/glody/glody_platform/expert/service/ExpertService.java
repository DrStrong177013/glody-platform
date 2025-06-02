package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.expert.repository.ExpertProfileRepository;
import com.glody.glody_platform.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertProfileRepository expertRepo;
    private final CountryRepository countryRepo;

    public Page<ExpertProfileDto> getExperts(String keyword, Pageable pageable) {
        Page<ExpertProfile> experts = expertRepo.searchExperts(keyword, pageable);
        return experts.map(this::mapToDto);
    }

    public List<String> getAllAdvisingCountries() {
        return countryRepo.findAll().stream()
                .map(Country::getName)
                .distinct()
                .toList();
    }
    public PageResponse<ExpertProfileDto> searchExperts(String country, Integer minYears, String keyword,
                                                        int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ExpertProfile> results = expertRepo.searchExperts(country, minYears, keyword, pageable);
        List<ExpertProfileDto> items = results.stream().map(this::mapToDto).toList();

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                results.getNumber(), results.getSize(),
                results.getTotalPages(), results.getTotalElements(),
                results.hasNext(), results.hasPrevious()
        );

        return new PageResponse<>(items, pageInfo);
    }


    private ExpertProfileDto mapToDto(ExpertProfile expert) {
        User user = expert.getUser();
        ExpertProfileDto dto = new ExpertProfileDto();
//        dto.setId(expert.getId());
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(expert.getAvatarUrl());
        dto.setBio(expert.getBio());
        dto.setExpertise(expert.getExpertise());
        dto.setExperience(expert.getExperience());
        dto.setYearsOfExperience(expert.getYearsOfExperience());

        List<String> countries = expert.getAdvisingCountries().stream()
                .map(Country::getName).toList();
        dto.setCountryNames(countries);
        return dto;
    }
}
