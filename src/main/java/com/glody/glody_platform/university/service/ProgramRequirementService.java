package com.glody.glody_platform.university.service;

import com.glody.glody_platform.university.dto.ProgramRequirementDto;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.university.repository.ProgramRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProgramRequirementService {

    private final ProgramRequirementRepository requirementRepository;
    private final ProgramRepository programRepository;

    public ProgramRequirementDto getByProgramId(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        ProgramRequirement requirement = program.getRequirement();
        if (requirement == null) throw new RuntimeException("No requirement defined for this program");

        return toDto(requirement);
    }

    public ProgramRequirementDto createOrUpdate(Long programId, ProgramRequirementDto dto) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        ProgramRequirement requirement = program.getRequirement();
        if (requirement == null) {
            requirement = new ProgramRequirement();
            requirement.setProgram(program);
        }

        requirement.setGpaRequirement(dto.getGpaRequirement());
        requirement.setLanguageRequirement(dto.getLanguageRequirement());
        requirement.setDocuments(dto.getDocuments());
        requirement.setDeadline(dto.getDeadline());

        ProgramRequirement saved = requirementRepository.save(requirement);
        return toDto(saved);
    }

    public void deleteByProgramId(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        if (program.getRequirement() != null) {
            requirementRepository.delete(program.getRequirement());
            program.setRequirement(null);
            programRepository.save(program);
        }
    }

    private ProgramRequirementDto toDto(ProgramRequirement entity) {
        ProgramRequirementDto dto = new ProgramRequirementDto();
        dto.setGpaRequirement(entity.getGpaRequirement());
        dto.setLanguageRequirement(entity.getLanguageRequirement());
        dto.setDocuments(entity.getDocuments());
        dto.setDeadline(entity.getDeadline());
        return dto;
    }
}
