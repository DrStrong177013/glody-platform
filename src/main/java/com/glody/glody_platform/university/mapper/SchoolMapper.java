package com.glody.glody_platform.university.mapper;

import com.glody.glody_platform.university.dto.*;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.entity.Program;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolResponseDto toDto(School entity) {
        SchoolResponseDto dto = new SchoolResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameEn(entity.getNameEn());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setLocation(entity.getLocation());
        dto.setWebsite(entity.getWebsite());
        dto.setRankText(entity.getRankText());
        dto.setIntroduction(entity.getIntroduction());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setPrograms(Optional.ofNullable(entity.getPrograms())
                .orElse(List.of())
                .stream()
                .map(ProgramMapper::toSimpleDto)
                .toList());

        dto.setScholarships(Optional.ofNullable(entity.getScholarships())
                .orElse(List.of())
                .stream()
                .map(ScholarshipMapper::toSimpleDto)
                .toList());


        return dto;
    }

    public static SchoolSimpleDto toSimpleDto(School entity) {
        SchoolSimpleDto dto = new SchoolSimpleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLogoUrl(entity.getLogoUrl());
        return dto;
    }
    public static void applyDtoToEntity(ProgramRequestDto dto, Program program, School school) {
        program.setSchool(school);
        program.setLevel(dto.getLevel());
        program.setLanguage(dto.getLanguage());
        program.setMajors(dto.getMajors());
        program.setTuitionFee(dto.getTuitionFee());
        program.setLivingCost(dto.getLivingCost());
        program.setDormFee(dto.getDormFee());
        program.setScholarshipSupport(dto.getScholarshipSupport());

        if (dto.getRequirement() != null) {
            ProgramRequirementDto rDto = dto.getRequirement();
            if (program.getRequirement() == null) {
                program.setRequirement(new ProgramRequirement());
                program.getRequirement().setProgram(program); // Bi-directional
            }
            program.getRequirement().setGpaRequirement(rDto.getGpaRequirement());
            program.getRequirement().setLanguageRequirement(rDto.getLanguageRequirement());
            program.getRequirement().setDocuments(rDto.getDocuments());
            program.getRequirement().setDeadline(rDto.getDeadline());
        }
    }

}
