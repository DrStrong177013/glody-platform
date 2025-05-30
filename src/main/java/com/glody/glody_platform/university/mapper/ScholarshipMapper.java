package com.glody.glody_platform.university.mapper;

import com.glody.glody_platform.university.dto.ScholarshipDetailDto;
import com.glody.glody_platform.university.dto.ScholarshipResponseDto;
import com.glody.glody_platform.university.dto.ScholarshipSimpleDto;
import com.glody.glody_platform.university.entity.Scholarship;

public class ScholarshipMapper {

    public static ScholarshipDetailDto toDetailDto(Scholarship scholarship) {
        ScholarshipDetailDto dto = new ScholarshipDetailDto();

        dto.setId(scholarship.getId());
        dto.setTitle(scholarship.getTitle());
        dto.setSponsor(scholarship.getSponsor());
        dto.setDescription(scholarship.getDescription());
        dto.setValue(scholarship.getValue());
        dto.setApplicationDeadline(scholarship.getApplicationDeadline());
        dto.setConditions(scholarship.getConditions());

        if (scholarship.getSchool() != null) {
            dto.setSchoolName(scholarship.getSchool().getName());
        }

        return dto;
    }
    public static ScholarshipSimpleDto toSimpleDto(Scholarship scholarship) {
        ScholarshipSimpleDto dto = new ScholarshipSimpleDto();
        dto.setId(scholarship.getId());
        dto.setTitle(scholarship.getTitle());
        dto.setSponsor(scholarship.getSponsor());
        dto.setValue(scholarship.getValue());
        return dto;
    }
    public static ScholarshipResponseDto toDto(Scholarship entity) {
        ScholarshipResponseDto dto = new ScholarshipResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setSponsor(entity.getSponsor());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setApplicationDeadline(entity.getApplicationDeadline());
        dto.setConditions(entity.getConditions());
        dto.setSchoolName(entity.getSchool() != null ? entity.getSchool().getName() : null);
        return dto;
    }
}
