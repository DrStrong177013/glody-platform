package com.glody.glody_platform.academic.controller;

import com.glody.glody_platform.academic.dto.ScholarshipRequirementDto;
import com.glody.glody_platform.academic.service.ScholarshipRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/requirements")
public class ScholarshipRequirementController {

    private final ScholarshipRequirementService requirementService;

    @GetMapping
    public ResponseEntity<List<ScholarshipRequirementDto>> getByScholarship(@RequestParam Long scholarshipId) {
        return ResponseEntity.ok(requirementService.getByScholarship(scholarshipId));
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid ScholarshipRequirementDto dto) {
        requirementService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ScholarshipRequirementDto dto) {
        dto.setId(id);
        requirementService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        requirementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
