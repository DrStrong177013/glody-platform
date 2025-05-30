// ScholarshipController.java
package com.glody.glody_platform.university.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.university.dto.ScholarshipRequestDto;
import com.glody.glody_platform.university.dto.ScholarshipResponseDto;
import com.glody.glody_platform.university.service.ScholarshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
@Tag(name = "Scholarship Controller", description = "Quản lý học bổng")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    @Operation(summary = "Lấy danh sách học bổng (search, filter, sort, paging)")
    public PageResponse<ScholarshipResponseDto> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return scholarshipService.getAll(keyword, schoolId, pageable);
    }


    @Operation(summary = "Chi tiết học bổng")
    @GetMapping("/{id}")
    public ScholarshipResponseDto getById(@PathVariable Long id) {
        return scholarshipService.getById(id);
    }

    @Operation(summary = "Admin tạo học bổng")
    @PostMapping("/admin")
    public ScholarshipResponseDto create(@RequestBody ScholarshipRequestDto dto) {
        return scholarshipService.create(dto);
    }

    @Operation(summary = "Admin cập nhật học bổng")
    @PutMapping("/admin/{id}")
    public ScholarshipResponseDto update(@PathVariable Long id, @RequestBody ScholarshipRequestDto dto) {
        return scholarshipService.update(id, dto);
    }

    @Operation(summary = "Admin xoá mềm học bổng")
    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        scholarshipService.delete(id);
    }
}
