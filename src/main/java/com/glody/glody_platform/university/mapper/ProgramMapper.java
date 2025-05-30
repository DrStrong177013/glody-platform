package com.glody.glody_platform.university.mapper;

import com.glody.glody_platform.university.dto.*;
import com.glody.glody_platform.university.entity.Program;

public class ProgramMapper {

    public static ProgramDetailDto toDetailDto(Program program) {
        ProgramDetailDto dto = new ProgramDetailDto();

        dto.setId(program.getId());
        dto.setLevel(program.getLevel().name());
        dto.setLanguage(program.getLanguage().name());
        dto.setMajors(program.getMajors());
        dto.setTuitionFee(program.getTuitionFee());
        dto.setLivingCost(program.getLivingCost());
        dto.setDormFee(program.getDormFee());
        dto.setScholarshipSupport(program.getScholarshipSupport());

        if (program.getRequirement() != null) {
            dto.setGpaRequirement(program.getRequirement().getGpaRequirement());
            dto.setLanguageRequirement(program.getRequirement().getLanguageRequirement());
            dto.setDocuments(program.getRequirement().getDocuments());
            dto.setDeadline(program.getRequirement().getDeadline());
        }

        return dto;
    }
    public static ProgramSimpleDto toSimpleDto(Program program) {
        ProgramSimpleDto dto = new ProgramSimpleDto();
        dto.setId(program.getId());
        dto.setLevel(program.getLevel().name());
        dto.setLanguage(program.getLanguage().name());
        dto.setMajors(program.getMajors());
        return dto;
    }
}
