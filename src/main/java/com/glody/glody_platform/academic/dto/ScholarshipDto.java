package com.glody.glody_platform.academic.dto;

import com.glody.glody_platform.academic.entity.Scholarship;
import com.glody.glody_platform.academic.entity.University;
import lombok.Data;

@Data
public class ScholarshipDto {
    private Long id;
    private String name;
    private String description;
    private Double value;
    private Long universityId;

    public static ScholarshipDto fromEntity(Scholarship entity) {
        ScholarshipDto dto = new ScholarshipDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setUniversityId(entity.getUniversity().getId());
        return dto;
    }

    public Scholarship toEntity() {
        Scholarship entity = new Scholarship();
        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        entity.setValue(value);
        University university = new University();
        university.setId(universityId);
        entity.setUniversity(university);
        return entity;
    }
}
