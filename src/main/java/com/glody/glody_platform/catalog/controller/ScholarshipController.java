package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ScholarshipRequestDto;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.service.ScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    public Page<Scholarship> search(
            @RequestParam(defaultValue = "") String keyword,
            Pageable pageable) {
        return scholarshipService.searchScholarships(keyword, pageable);
    }

    @PostMapping
    public Scholarship create(@RequestBody ScholarshipRequestDto dto) {
        return scholarshipService.create(dto);
    }

    @PutMapping("/{id}")
    public Scholarship update(@PathVariable Long id, @RequestBody ScholarshipRequestDto dto) {
        return scholarshipService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        scholarshipService.softDelete(id);
        return ResponseEntity.ok("Scholarship soft deleted successfully");
    }
}
