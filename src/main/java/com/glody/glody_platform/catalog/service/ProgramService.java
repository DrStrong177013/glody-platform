package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ProgramRequestDto;
import com.glody.glody_platform.catalog.dto.ProgramResponseDto;
import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.catalog.repository.ProgramRepository;
import com.glody.glody_platform.catalog.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final UniversityRepository universityRepository;

    public Page<ProgramResponseDto> searchPaged(String keyword, Pageable pageable) {
        return programRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable)
                .map(this::toDto);
    }

    public List<ProgramResponseDto> searchAll(String keyword, Sort sort) {
        return programRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, sort)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public Program create(ProgramRequestDto dto) {
        Program p = new Program();
        p.setName(dto.getName());
        p.setMajor(dto.getMajor()); // ❗️thêm dòng này
        p.setDegreeType(dto.getDegreeType());
        p.setUniversity(universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found")));
        return programRepository.save(p);
    }

    public Program update(Long id, ProgramRequestDto dto) {
        Program p = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        p.setName(dto.getName());
        p.setMajor(dto.getMajor()); // ❗️thêm dòng này
        p.setDegreeType(dto.getDegreeType());
        p.setUniversity(universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found")));
        return programRepository.save(p);
    }


    public void softDelete(Long id) {
        Program p = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        p.setIsDeleted(true);
        p.setDeletedAt(LocalDateTime.now());
        programRepository.save(p);
    }

    private ProgramResponseDto toDto(Program p) {
        return ProgramResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .major(p.getMajor())
                .degreeType(p.getDegreeType())
                .universityId(p.getUniversity().getId())
                .universityName(p.getUniversity().getName())
                .build();
    }
}
