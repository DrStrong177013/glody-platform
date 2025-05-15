package com.glody.glody_platform.academic.service;

import com.glody.glody_platform.academic.dto.ScholarshipDto;
import com.glody.glody_platform.academic.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public List<ScholarshipDto> getAll() {
        return scholarshipRepository.findAll().stream().map(ScholarshipDto::fromEntity).toList();
    }

    public List<ScholarshipDto> getByUniversity(Long universityId) {
        return scholarshipRepository.findByUniversityId(universityId).stream().map(ScholarshipDto::fromEntity).toList();
    }

    public void add(ScholarshipDto dto) {
        scholarshipRepository.save(dto.toEntity());
    }

    public void update(ScholarshipDto dto) {
        scholarshipRepository.save(dto.toEntity());
    }

    public void delete(Long id) {
        scholarshipRepository.deleteById(id);
    }
}
