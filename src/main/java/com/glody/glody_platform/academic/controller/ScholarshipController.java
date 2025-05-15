package com.glody.glody_platform.academic.controller;

import com.glody.glody_platform.academic.dto.ScholarshipDto;
import com.glody.glody_platform.academic.service.ScholarshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/scholarships")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    public ResponseEntity<List<ScholarshipDto>> getAll(@RequestParam(required = false) Long universityId) {
        if (universityId != null) {
            return ResponseEntity.ok(scholarshipService.getByUniversity(universityId));
        }
        return ResponseEntity.ok(scholarshipService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid ScholarshipDto dto) {
        scholarshipService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ScholarshipDto dto) {
        dto.setId(id);
        scholarshipService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scholarshipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
