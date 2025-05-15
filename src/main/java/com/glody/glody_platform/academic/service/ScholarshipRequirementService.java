package com.glody.glody_platform.academic.service;

import com.glody.glody_platform.academic.dto.ScholarshipRequirementDto;
import com.glody.glody_platform.academic.repository.ScholarshipRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipRequirementService {

    private final ScholarshipRequirementRepository requirementRepository;

    public List<ScholarshipRequirementDto> getByScholarship(Long scholarshipId) {
        return requirementRepository.findByScholarshipId(scholarshipId).stream().map(ScholarshipRequirementDto::fromEntity).toList();
    }

    public void add(ScholarshipRequirementDto dto) {
        requirementRepository.save(dto.toEntity());
    }

    public void update(ScholarshipRequirementDto dto) {
        requirementRepository.save(dto.toEntity());
    }

    public void delete(Long id) {
        requirementRepository.deleteById(id);
    }
}
