package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.ScholarshipRequestDto;
import com.glody.glody_platform.catalog.dto.ScholarshipResponseDto;
import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.entity.ProgramScholarship;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.repository.ProgramRepository;
import com.glody.glody_platform.catalog.repository.ProgramScholarshipRepository;
import com.glody.glody_platform.catalog.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final ProgramRepository programRepository;
    private final ProgramScholarshipRepository programScholarshipRepository;

    public Scholarship create(ScholarshipRequestDto dto) {
        Scholarship s = new Scholarship();
        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setMinGpa(dto.getMinGpa());
        s.setApplicableMajors(dto.getApplicableMajors());

        Scholarship saved = scholarshipRepository.save(s);

        // ✅ Nếu có danh sách chương trình, tạo liên kết qua bảng nối
        if (dto.getProgramIds() != null && !dto.getProgramIds().isEmpty()) {
            for (Long programId : dto.getProgramIds()) {
                Program program = programRepository.findById(programId)
                        .orElseThrow(() -> new RuntimeException("Program not found: " + programId));

                ProgramScholarship ps = new ProgramScholarship();
                ps.setProgram(program);
                ps.setScholarship(saved);
                programScholarshipRepository.save(ps);
            }
        }

        return saved;
    }

    public Scholarship update(Long id, ScholarshipRequestDto dto) {
        Scholarship s = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));

        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setMinGpa(dto.getMinGpa());
        s.setApplicableMajors(dto.getApplicableMajors());

        // ❗ Không xử lý programIds khi update để tránh logic phức tạp
        return scholarshipRepository.save(s);
    }

    public void softDelete(Long id) {
        Scholarship s = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        s.setIsDeleted(true);
        s.setDeletedAt(LocalDateTime.now());
        scholarshipRepository.save(s);
    }

    // Mapping sang DTO như cũ
    public Page<ScholarshipResponseDto> searchPaged(String keyword, Pageable pageable) {
        return scholarshipRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable)
                .map(this::toDto);
    }

    public List<ScholarshipResponseDto> searchAll(String keyword, Sort sort) {
        return scholarshipRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, sort)
                .stream().map(this::toDto).toList();
    }

    private ScholarshipResponseDto toDto(Scholarship s) {
        return ScholarshipResponseDto.builder()
                .id(s.getId())
                .name(s.getName())
                .description(s.getDescription())
                .minGpa(s.getMinGpa())
                .applicableMajors(s.getApplicableMajors())
                .build();
    }
}

