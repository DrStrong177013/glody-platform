package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ScholarshipRequestDto;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public Page<Scholarship> searchScholarships(String keyword, Pageable pageable) {
        return scholarshipRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable);
    }

    public Scholarship create(ScholarshipRequestDto dto) {
        Scholarship s = new Scholarship();
        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        return scholarshipRepository.save(s);
    }

    public Scholarship update(Long id, ScholarshipRequestDto dto) {
        Scholarship s = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        return scholarshipRepository.save(s);
    }

    public void softDelete(Long id) {
        Scholarship s = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        s.setIsDeleted(true);
        s.setDeletedAt(LocalDateTime.now());
        scholarshipRepository.save(s);
    }
}
