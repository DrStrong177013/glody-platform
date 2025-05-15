package com.glody.glody_platform.academic.controller;

import com.glody.glody_platform.academic.dto.UniversityDto;
import com.glody.glody_platform.academic.service.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<List<UniversityDto>> getByCountry(@RequestParam Long countryId) {
        return ResponseEntity.ok(universityService.getByCountry(countryId));
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid UniversityDto dto) {
        universityService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UniversityDto dto) {
        dto.setId(id);
        universityService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        universityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
