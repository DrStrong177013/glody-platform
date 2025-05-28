package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ScholarshipRequirementRequestDto;
import com.glody.glody_platform.catalog.dto.ScholarshipRequirementResponseDto;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import com.glody.glody_platform.catalog.repository.ScholarshipRepository;
import com.glody.glody_platform.catalog.repository.ScholarshipRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipRequirementService {

    private final ScholarshipRepository scholarshipRepository;
    private final ScholarshipRequirementRepository requirementRepository;

    public List<ScholarshipRequirementResponseDto> getByScholarship(Long scholarshipId) {
        return requirementRepository.findByScholarshipIdAndIsDeletedFalse(scholarshipId)
                .stream()
                .map(this::toDto)
                .toList();
    }
    public List<ScholarshipRequirementResponseDto> getAll() {
        return requirementRepository.findByIsDeletedFalse()
                .stream()
                .map(this::toDto)
                .toList();
    }


    public ScholarshipRequirementResponseDto create(ScholarshipRequirementRequestDto dto) {
        Scholarship scholarship = scholarshipRepository.findById(dto.getScholarshipId())
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        ScholarshipRequirement req = new ScholarshipRequirement();
        req.setScholarship(scholarship);
        req.setRequirementDetail(dto.getRequirementDetail());

        // ✅ THIẾU các dòng này:
        req.setRequirementType(dto.getRequirementType());
        req.setRequirementCategory(dto.getRequirementCategory());
        req.setMinValue(dto.getMinValue());
        req.setMaxValue(dto.getMaxValue());
        req.setUnit(dto.getUnit());

        return toDto(requirementRepository.save(req));
    }


    public ScholarshipRequirementResponseDto update(Long id, ScholarshipRequirementRequestDto dto) {
        ScholarshipRequirement req = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));

        req.setRequirementDetail(dto.getRequirementDetail());

        // ✅ THIẾU các dòng này:
        req.setRequirementType(dto.getRequirementType());
        req.setRequirementCategory(dto.getRequirementCategory());
        req.setMinValue(dto.getMinValue());
        req.setMaxValue(dto.getMaxValue());
        req.setUnit(dto.getUnit());

        return toDto(requirementRepository.save(req));
    }


    public void softDelete(Long id) {
        ScholarshipRequirement req = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        req.setIsDeleted(true);
        req.setDeletedAt(LocalDateTime.now());
        requirementRepository.save(req);
    }
    public Page<ScholarshipRequirementResponseDto> searchPaged(Long scholarshipId, Pageable pageable) {
        Page<ScholarshipRequirement> page;

        if (scholarshipId != null) {
            page = requirementRepository.findByScholarshipIdAndIsDeletedFalse(scholarshipId, pageable);
        } else {
            page = requirementRepository.findByIsDeletedFalse(pageable);
        }

        return page.map(this::toDto);
    }

    public List<ScholarshipRequirementResponseDto> searchAll(Long scholarshipId, Sort sort) {
        List<ScholarshipRequirement> list;

        if (scholarshipId != null) {
            list = requirementRepository.findByScholarshipIdAndIsDeletedFalse(scholarshipId, sort);
        } else {
            list = requirementRepository.findByIsDeletedFalse(sort);
        }

        return list.stream().map(this::toDto).toList();
    }


    private ScholarshipRequirementResponseDto toDto(ScholarshipRequirement req) {
        return ScholarshipRequirementResponseDto.builder()
                .id(req.getId())
                .requirementType(req.getRequirementType())
                .requirementCategory(req.getRequirementCategory())
                .requirementDetail(req.getRequirementDetail())
                .minValue(req.getMinValue())
                .maxValue(req.getMaxValue())
                .unit(req.getUnit())
                .scholarshipId(req.getScholarship().getId())
                .build();
    }
}

