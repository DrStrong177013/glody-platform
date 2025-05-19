package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ScholarshipRequirementRequestDto;
import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import com.glody.glody_platform.catalog.service.ScholarshipRequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship-requirements")
@RequiredArgsConstructor
public class ScholarshipRequirementController {

    private final ScholarshipRequirementService service;

    @GetMapping("/{scholarshipId}")
    public List<ScholarshipRequirement> getByScholarship(@PathVariable Long scholarshipId) {
        return service.getByScholarship(scholarshipId);
    }

    @PostMapping
    public ScholarshipRequirement create(@RequestBody ScholarshipRequirementRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ScholarshipRequirement update(@PathVariable Long id, @RequestBody ScholarshipRequirementRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.ok("Requirement soft deleted");
    }
}
