package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.CountryRequestDto;
import com.glody.glody_platform.catalog.dto.CountryResponseDto;
import com.glody.glody_platform.catalog.dto.UniversityDto;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<Country> searchAll(String keyword, Sort sort) {
        return countryRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, sort);
    }

    public Page<Country> searchPaged(String keyword, Pageable pageable) {
        return countryRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable);
    }

    public List<CountryResponseDto> searchAllDto(String keyword, Sort sort) {
        return searchAll(keyword, sort).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public CountryResponseDto getById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        return toDto(country);
    }

    public Page<CountryResponseDto> searchPagedDto(String keyword, Pageable pageable) {
        return searchPaged(keyword, pageable).map(this::toDto);
    }

    public Country create(CountryRequestDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        return countryRepository.save(country);
    }

    public Country update(Long id, CountryRequestDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        return countryRepository.save(country);
    }

    public void softDelete(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        country.setIsDeleted(true);
        countryRepository.save(country);
    }

    private CountryResponseDto toDto(Country country) {
        CountryResponseDto dto = new CountryResponseDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setCode(country.getCode());

        List<UniversityDto> universityDtos = country.getUniversities().stream()
                .filter(u -> !u.getIsDeleted())
                .map(u -> {
                    UniversityDto ud = new UniversityDto();
                    ud.setId(u.getId());
                    ud.setName(u.getName());
                    return ud;
                }).collect(Collectors.toList());

        dto.setUniversities(universityDtos);
        return dto;
    }
}
