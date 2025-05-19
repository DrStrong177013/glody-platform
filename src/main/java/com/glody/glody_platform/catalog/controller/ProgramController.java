package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ProgramRequestDto;
import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public Page<Program> search(
            @RequestParam(defaultValue = "") String keyword,
            Pageable pageable) {
        return programService.searchPrograms(keyword, pageable);
    }

    @PostMapping
    public Program create(@RequestBody ProgramRequestDto dto) {
        return programService.create(dto);
    }

    @PutMapping("/{id}")
    public Program update(@PathVariable Long id, @RequestBody ProgramRequestDto dto) {
        return programService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        programService.softDelete(id);
        return ResponseEntity.ok("Program soft deleted successfully");
    }
}
