package com.glody.glody_platform.university.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.SchoolRequestDto;
import com.glody.glody_platform.university.dto.SchoolResponseDto;
import com.glody.glody_platform.university.dto.SchoolSimpleDto;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.university.mapper.SchoolMapper;
import com.glody.glody_platform.university.repository.SchoolRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public PageResponse<SchoolSimpleDto> getSchools(String keyword, Pageable pageable) {
        Specification<School> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            predicates.add(cb.isFalse(root.get("isDeleted")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<School> pageResult = schoolRepository.findAll(spec, pageable);
        List<SchoolSimpleDto> content = pageResult.getContent().stream()
                .map(SchoolMapper::toSimpleDto)
                .toList();

        return new PageResponse<>(
                content,
                new PageResponse.PageInfo(
                        pageResult.getNumber(),
                        pageResult.getSize(),
                        pageResult.getTotalPages(),
                        pageResult.getTotalElements(),
                        pageResult.hasNext(),
                        pageResult.hasPrevious()
                )
        );

    }



    public SchoolResponseDto getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .filter(s -> !Boolean.TRUE.equals(s.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("School not found"));
        return SchoolMapper.toDto(school);
    }

    public SchoolResponseDto createSchool(SchoolRequestDto dto) {
        School school = new School();
        applyDtoToEntity(dto, school);
        School saved = schoolRepository.save(school);
        return SchoolMapper.toDto(saved);
    }

    public SchoolResponseDto updateSchool(Long id, SchoolRequestDto dto) {
        School school = schoolRepository.findById(id)
                .filter(s -> !Boolean.TRUE.equals(s.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("School not found"));

        applyDtoToEntity(dto, school);
        School updated = schoolRepository.save(school);
        return SchoolMapper.toDto(updated);
    }

    public void softDelete(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        school.setIsDeleted(true);
        school.setDeletedAt(LocalDateTime.now());
        schoolRepository.save(school);
    }

    private void applyDtoToEntity(SchoolRequestDto dto, School school) {
        school.setName(dto.getName());
        school.setNameEn(dto.getNameEn());
        school.setLocation(dto.getLocation());
        school.setWebsite(dto.getWebsite());
        school.setLogoUrl(dto.getLogoUrl());
        school.setRankText(dto.getRankText());
        school.setIntroduction(dto.getIntroduction());
        school.setEstablishedYear(dto.getEstablishedYear());
    }
}
