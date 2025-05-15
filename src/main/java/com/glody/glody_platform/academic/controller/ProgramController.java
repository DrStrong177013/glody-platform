package com.glody.glody_platform.academic.controller;

import com.glody.glody_platform.academic.dto.ProgramDto;
import com.glody.glody_platform.academic.service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public ResponseEntity<List<ProgramDto>> getAll(@RequestParam(required = false) Long universityId) {
        if (universityId != null) {
            return ResponseEntity.ok(programService.getByUniversity(universityId));
        }
        return ResponseEntity.ok(programService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid ProgramDto dto) {
        programService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ProgramDto dto) {
        dto.setId(id);
        programService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
