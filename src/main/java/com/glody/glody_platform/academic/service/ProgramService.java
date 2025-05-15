package com.glody.glody_platform.academic.service;

import com.glody.glody_platform.academic.dto.ProgramDto;
import com.glody.glody_platform.academic.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public List<ProgramDto> getAll() {
        return programRepository.findAll().stream().map(ProgramDto::fromEntity).toList();
    }

    public List<ProgramDto> getByUniversity(Long universityId) {
        return programRepository.findByUniversityId(universityId).stream().map(ProgramDto::fromEntity).toList();
    }

    public void add(ProgramDto dto) {
        programRepository.save(dto.toEntity());
    }

    public void update(ProgramDto dto) {
        programRepository.save(dto.toEntity());
    }

    public void delete(Long id) {
        programRepository.deleteById(id);
    }
}
