package com.glody.glody_platform.academic.dto;

import com.glody.glody_platform.academic.entity.Scholarship;
import com.glody.glody_platform.academic.entity.ScholarshipRequirement;
import lombok.Data;

@Data
public class ScholarshipRequirementDto {
    private Long id;
    private String criteria;
    private String requirement;
    private Long scholarshipId;

    public static ScholarshipRequirementDto fromEntity(ScholarshipRequirement entity) {
        ScholarshipRequirementDto dto = new ScholarshipRequirementDto();
        dto.setId(entity.getId());
        dto.setCriteria(entity.getCriteria());
        dto.setRequirement(entity.getRequirement());
        dto.setScholarshipId(entity.getScholarship().getId());
        return dto;
    }

    public ScholarshipRequirement toEntity() {
        ScholarshipRequirement entity = new ScholarshipRequirement();
        entity.setId(id);
        entity.setCriteria(criteria);
        entity.setRequirement(requirement);
        Scholarship scholarship = new Scholarship();
        scholarship.setId(scholarshipId);
        entity.setScholarship(scholarship);
        return entity;
    }
}
