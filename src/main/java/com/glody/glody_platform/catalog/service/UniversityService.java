package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.UniversityRequestDto;
import com.glody.glody_platform.catalog.dto.UniversityResponseDto;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import com.glody.glody_platform.catalog.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final CountryRepository countryRepository;

    public Page<UniversityResponseDto> searchPaged(String keyword, Pageable pageable) {
        return universityRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable)
                .map(this::toDto);
    }

    public List<UniversityResponseDto> searchAll(String keyword, Sort sort) {
        return universityRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, sort)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public University create(UniversityRequestDto dto) {
        University u = new University();
        u.setName(dto.getName());
        u.setCountry(countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found")));
        return universityRepository.save(u);
    }

    public University update(Long id, UniversityRequestDto dto) {
        University u = universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        u.setName(dto.getName());
        u.setCountry(countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found")));
        return universityRepository.save(u);
    }

    public void softDelete(Long id) {
        University u = universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        u.setIsDeleted(true);
        u.setDeletedAt(LocalDateTime.now());
        universityRepository.save(u);
    }

    private UniversityResponseDto toDto(University u) {
        UniversityResponseDto dto = new UniversityResponseDto();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setCountryId(u.getCountry().getId());
        dto.setCountryName(u.getCountry().getName());
        return dto;
    }
}
