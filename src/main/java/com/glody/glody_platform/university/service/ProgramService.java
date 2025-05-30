package com.glody.glody_platform.university.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.ProgramDetailDto;
import com.glody.glody_platform.university.dto.ProgramRequestDto;
import com.glody.glody_platform.university.dto.ProgramSimpleDto;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.university.enums.LanguageType;
import com.glody.glody_platform.university.mapper.ProgramMapper;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.university.repository.SchoolRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final SchoolRepository schoolRepository;

    public PageResponse<ProgramSimpleDto> getPrograms(Long schoolId, DegreeLevel level, LanguageType language, String keyword, Pageable pageable) {
        Specification<Program> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (schoolId != null) {
                predicates.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            if (level != null) {
                predicates.add(cb.equal(root.get("level"), level));
            }
            if (language != null) {
                predicates.add(cb.equal(root.get("language"), language));
            }
            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(cb.lower(root.join("majors")), "%" + keyword.toLowerCase() + "%"));
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Program> page = programRepository.findAll(spec, pageable);
        List<ProgramSimpleDto> items = page.getContent().stream()
                .map(ProgramMapper::toSimpleDto)
                .toList();

        return new PageResponse<>(
                items,
                new PageResponse.PageInfo(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalPages(),
                        page.getTotalElements(),
                        page.hasNext(),
                        page.hasPrevious()
                )
        );
    }

    public ProgramDetailDto getById(Long id) {
        Program program = programRepository.findById(id)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Program not found"));
        return ProgramMapper.toDetailDto(program);
    }

    public ProgramDetailDto create(Long schoolId, ProgramRequestDto dto) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        Program program = new Program();
        program.setSchool(school);
        applyDtoToEntity(dto, program);
        return ProgramMapper.toDetailDto(programRepository.save(program));
    }

    public ProgramDetailDto update(Long id, ProgramRequestDto dto) {
        Program program = programRepository.findById(id)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Program not found"));
        applyDtoToEntity(dto, program);
        return ProgramMapper.toDetailDto(programRepository.save(program));
    }

    public void delete(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        program.setIsDeleted(true);
        program.setDeletedAt(LocalDateTime.now());
        programRepository.save(program);
    }

    private void applyDtoToEntity(ProgramRequestDto dto, Program program) {
        program.setLevel(dto.getLevel());
        program.setLanguage(dto.getLanguage());
        program.setMajors(dto.getMajors());
        program.setTuitionFee(dto.getTuitionFee());
        program.setLivingCost(dto.getLivingCost());
        program.setDormFee(dto.getDormFee());
        program.setScholarshipSupport(dto.getScholarshipSupport());

        if (dto.getRequirement() != null) {
            ProgramRequirement requirement = program.getRequirement() != null ? program.getRequirement() : new ProgramRequirement();
            requirement.setProgram(program);
            requirement.setGpaRequirement(dto.getRequirement().getGpaRequirement());
            requirement.setLanguageRequirement(dto.getRequirement().getLanguageRequirement());
            requirement.setDocuments(dto.getRequirement().getDocuments());
            requirement.setDeadline(dto.getRequirement().getDeadline());
            program.setRequirement(requirement);
        }
    }
}
