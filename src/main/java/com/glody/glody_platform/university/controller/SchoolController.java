package com.glody.glody_platform.university.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.SchoolRequestDto;
import com.glody.glody_platform.university.dto.SchoolResponseDto;
import com.glody.glody_platform.university.dto.SchoolSimpleDto;
import com.glody.glody_platform.university.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@Tag(name = "School Controller", description = "Quản lý trường học")
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "Lấy danh sách trường học (filter, search, sort, pagination)")
    @GetMapping
    public PageResponse<SchoolSimpleDto> getSchools(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return schoolService.getSchools(keyword, pageable);
    }


    @Operation(summary = "Lấy chi tiết trường học")
    @GetMapping("/{id}")
    public SchoolResponseDto getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @Operation(summary = "Tạo mới trường học (Admin)")
    @PostMapping("/admin")
    public SchoolResponseDto create(@RequestBody SchoolRequestDto dto) {
        return schoolService.createSchool(dto);
    }

    @Operation(summary = "Cập nhật thông tin trường học (Admin)")
    @PutMapping("/admin/{id}")
    public SchoolResponseDto update(@PathVariable Long id,
                                    @RequestBody SchoolRequestDto dto) {
        return schoolService.updateSchool(id, dto);
    }

    @Operation(summary = "Xoá mềm trường học (Admin)")
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable Long id) {
        schoolService.softDelete(id);
        return "Trường đã được xoá mềm.";
    }
}
