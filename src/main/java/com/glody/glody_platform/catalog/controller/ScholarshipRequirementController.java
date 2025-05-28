package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ScholarshipRequirementRequestDto;
import com.glody.glody_platform.catalog.dto.ScholarshipRequirementResponseDto;
import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import com.glody.glody_platform.catalog.service.ScholarshipRequirementService;
import com.glody.glody_platform.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship-requirements")
@RequiredArgsConstructor
public class ScholarshipRequirementController {

    private final ScholarshipRequirementService scholarshipRequirementService;

    @GetMapping
    public ResponseEntity<?> getPagedOrFiltered(
            @RequestParam(required = false) Long scholarshipId,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Nếu có size => phân trang
        if (size != null) {
            int pageNumber = (page != null) ? page : 0;
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            Page<ScholarshipRequirementResponseDto> paged = scholarshipRequirementService.searchPaged(scholarshipId, pageable);

            PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                    paged.getNumber(),
                    paged.getSize(),
                    paged.getTotalPages(),
                    paged.getTotalElements(),
                    paged.hasNext(),
                    paged.hasPrevious()
            );

            return ResponseEntity.ok(new PageResponse<>(paged.getContent(), pageInfo));
        }

        // Không phân trang -> trả toàn bộ
        List<ScholarshipRequirementResponseDto> list = scholarshipRequirementService.searchAll(scholarshipId, sort);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0, list.size(), 1, list.size(), false, false
        );

        return ResponseEntity.ok(new PageResponse<>(list, pageInfo));
    }

    @PostMapping
    public ResponseEntity<ScholarshipRequirementResponseDto> create(@RequestBody @Valid ScholarshipRequirementRequestDto dto) {
        return ResponseEntity.ok(scholarshipRequirementService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScholarshipRequirementResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ScholarshipRequirementRequestDto dto) {
        return ResponseEntity.ok(scholarshipRequirementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        scholarshipRequirementService.softDelete(id);
        return ResponseEntity.ok("Requirement soft deleted successfully");
    }
}

