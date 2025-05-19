package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ScholarshipRequirementRequestDto;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import com.glody.glody_platform.catalog.repository.ScholarshipRepository;
import com.glody.glody_platform.catalog.repository.ScholarshipRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipRequirementService {

    private final ScholarshipRepository scholarshipRepository;
    private final ScholarshipRequirementRepository requirementRepository;

    public List<ScholarshipRequirement> getByScholarship(Long scholarshipId) {
        return requirementRepository.findByScholarshipIdAndIsDeletedFalse(scholarshipId);
    }

    public ScholarshipRequirement create(ScholarshipRequirementRequestDto dto) {
        Scholarship scholarship = scholarshipRepository.findById(dto.getScholarshipId())
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        ScholarshipRequirement req = new ScholarshipRequirement();
        req.setScholarship(scholarship);
        req.setRequirementDetail(dto.getRequirementDetail());
        return requirementRepository.save(req);
    }

    public ScholarshipRequirement update(Long id, ScholarshipRequirementRequestDto dto) {
        ScholarshipRequirement req = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));

        req.setRequirementDetail(dto.getRequirementDetail());
        return requirementRepository.save(req);
    }

    public void softDelete(Long id) {
        ScholarshipRequirement req = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        req.setIsDeleted(true);
        req.setDeletedAt(LocalDateTime.now());
        requirementRepository.save(req);
    }
}
