package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ScholarshipRequirementRequestDto;
import com.glody.glody_platform.catalog.dto.ScholarshipRequirementResponseDto;
import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import com.glody.glody_platform.catalog.service.ScholarshipRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship-requirements")
@RequiredArgsConstructor
public class ScholarshipRequirementController {

    private final ScholarshipRequirementService scholarshipRequirementService;

    @GetMapping
    public ResponseEntity<List<ScholarshipRequirementResponseDto>> getAllOrByScholarship(
            @RequestParam(required = false) Long scholarshipId) {
        List<ScholarshipRequirementResponseDto> result;

        if (scholarshipId != null) {
            result = scholarshipRequirementService.getByScholarship(scholarshipId);
        } else {
            result = scholarshipRequirementService.getAll();
        }

        return ResponseEntity.ok(result);
    }



    @PostMapping
    public ResponseEntity<ScholarshipRequirementResponseDto> create(@RequestBody @Valid ScholarshipRequirementRequestDto dto) {
        return ResponseEntity.ok(scholarshipRequirementService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScholarshipRequirementResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ScholarshipRequirementRequestDto dto) {
        return ResponseEntity.ok(scholarshipRequirementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        scholarshipRequirementService.softDelete(id);
        return ResponseEntity.ok("Requirement soft deleted successfully");
    }
}
