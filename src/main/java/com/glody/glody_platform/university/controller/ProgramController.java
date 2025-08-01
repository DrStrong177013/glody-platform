package com.glody.glody_platform.university.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.ProgramDetailDto;
import com.glody.glody_platform.university.dto.ProgramRequestDto;
import com.glody.glody_platform.university.dto.ProgramSimpleDto;
import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.university.enums.LanguageType;
import com.glody.glody_platform.university.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
@Tag(name = "Program Controller", description = "Quản lý chương trình học")
public class ProgramController {
    private final ProgramService programService;
    @GetMapping
    @Operation(summary = "Lấy danh sách chương trình học có filter + pagination + sort")
    public PageResponse<ProgramSimpleDto> getPrograms(
            @RequestParam(required = false) Long schoolId,
            @RequestParam(required = false) DegreeLevel level,
            @RequestParam(required = false) LanguageType language,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return programService.getPrograms(schoolId, level, language, keyword, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết 1 chương trình học")
    public ProgramDetailDto getById(@PathVariable Long id) {
        return programService.getById(id);
    }

    @PostMapping("/admin")
    @Operation(summary = "Tạo chương trình mới (Admin)")
    public ProgramDetailDto create(@RequestParam Long schoolId, @RequestBody ProgramRequestDto dto) {
        return programService.create(schoolId, dto);
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Cập nhật chương trình (Admin)")
    public ProgramDetailDto update(@PathVariable Long id, @RequestBody ProgramRequestDto dto) {
        return programService.update(id, dto);
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Xoá mềm chương trình (Admin)")
    public String delete(@PathVariable Long id) {
        programService.delete(id);
        return "Deleted";
    }
}
