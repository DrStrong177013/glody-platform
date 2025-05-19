package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.UniversityDto;
import com.glody.glody_platform.catalog.dto.UniversityRequestDto;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.catalog.service.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Pageable;
import java.util.List;


@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping
    public Page<University> search(
            @RequestParam(defaultValue = "") String keyword,
            Pageable pageable) {
        return universityService.searchUniversities(keyword, pageable);
    }

    @PostMapping
    public University create(@RequestBody UniversityRequestDto dto) {
        return universityService.create(dto);
    }

    @PutMapping("/{id}")
    public University update(@PathVariable Long id, @RequestBody UniversityRequestDto dto) {
        return universityService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        universityService.softDelete(id);
        return ResponseEntity.ok("University deleted (soft)");
    }
}

