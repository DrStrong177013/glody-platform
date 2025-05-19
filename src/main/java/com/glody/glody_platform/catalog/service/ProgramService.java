package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ProgramRequestDto;
import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.catalog.repository.ProgramRepository;
import com.glody.glody_platform.catalog.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final UniversityRepository universityRepository;

    public Page<Program> searchPrograms(String keyword, Pageable pageable) {
        return programRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable);
    }

    public Program create(ProgramRequestDto dto) {
        University university = universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        Program p = new Program();
        p.setName(dto.getName());
        p.setDegreeType(dto.getDegreeType());
        p.setUniversity(university);
        return programRepository.save(p);
    }

    public Program update(Long id, ProgramRequestDto dto) {
        Program p = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        University university = universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        p.setName(dto.getName());
        p.setDegreeType(dto.getDegreeType());
        p.setUniversity(university);
        return programRepository.save(p);
    }

    public void softDelete(Long id) {
        Program p = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        p.setIsDeleted(true);
        p.setDeletedAt(LocalDateTime.now());
        programRepository.save(p);
    }
}
