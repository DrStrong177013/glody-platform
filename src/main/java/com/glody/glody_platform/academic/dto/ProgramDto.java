package com.glody.glody_platform.academic.dto;

import com.glody.glody_platform.academic.entity.Program;
import com.glody.glody_platform.academic.entity.University;
import lombok.Data;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String level;
    private Long universityId;

    public static ProgramDto fromEntity(Program entity) {
        ProgramDto dto = new ProgramDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLevel(entity.getLevel());
        dto.setUniversityId(entity.getUniversity().getId());
        return dto;
    }

    public Program toEntity() {
        Program entity = new Program();
        entity.setId(id);
        entity.setName(name);
        entity.setLevel(level);
        University university = new University();
        university.setId(universityId);
        entity.setUniversity(university);
        return entity;
    }
}
