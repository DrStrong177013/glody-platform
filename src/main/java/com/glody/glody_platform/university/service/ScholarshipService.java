package com.glody.glody_platform.university.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.ScholarshipRequestDto;
import com.glody.glody_platform.university.dto.ScholarshipResponseDto;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.university.mapper.ScholarshipMapper;
import com.glody.glody_platform.university.repository.ScholarshipRepository;
import com.glody.glody_platform.university.repository.SchoolRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final SchoolRepository schoolRepository;

    public PageResponse<ScholarshipResponseDto> getAll(String keyword, Long schoolId, Pageable pageable) {
        Specification<Scholarship> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"));
            }

            if (schoolId != null) {
                predicates.add(cb.equal(root.get("school").get("id"), schoolId));
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Scholarship> page = scholarshipRepository.findAll(spec, pageable);
        List<ScholarshipResponseDto> dtos = page.getContent().stream().map(ScholarshipMapper::toDto).toList();

        return new PageResponse<>(dtos, new PageResponse.PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious()
        ));
    }

    public ScholarshipResponseDto getById(Long id) {
        Scholarship entity = scholarshipRepository.findById(id)
                .filter(s -> !Boolean.TRUE.equals(s.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        return ScholarshipMapper.toDto(entity);
    }

    public ScholarshipResponseDto create(ScholarshipRequestDto dto) {
        Scholarship entity = new Scholarship();
        applyDtoToEntity(dto, entity);
        return ScholarshipMapper.toDto(scholarshipRepository.save(entity));
    }

    public ScholarshipResponseDto update(Long id, ScholarshipRequestDto dto) {
        Scholarship entity = scholarshipRepository.findById(id)
                .filter(s -> !Boolean.TRUE.equals(s.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        applyDtoToEntity(dto, entity);
        return ScholarshipMapper.toDto(scholarshipRepository.save(entity));
    }

    public void delete(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        scholarship.setIsDeleted(true);
        scholarship.setDeletedAt(LocalDateTime.now());
        scholarshipRepository.save(scholarship);
    }

    private void applyDtoToEntity(ScholarshipRequestDto dto, Scholarship entity) {
        entity.setTitle(dto.getTitle());
        entity.setSponsor(dto.getSponsor());
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
        entity.setApplicationDeadline(dto.getApplicationDeadline());
        entity.setConditions(dto.getConditions());

        if (dto.getSchoolId() != null) {
            School school = schoolRepository.findById(dto.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));
            entity.setSchool(school);
        }
    }
}
