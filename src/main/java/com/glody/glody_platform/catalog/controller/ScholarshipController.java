package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ScholarshipRequestDto;
import com.glody.glody_platform.catalog.dto.ScholarshipResponseDto;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.service.ScholarshipService;
import com.glody.glody_platform.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (size != null) {
            int pageNumber = (page != null) ? page : 0;
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            Page<ScholarshipResponseDto> paged = scholarshipService.searchPaged(keyword, pageable);

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

        List<ScholarshipResponseDto> list = scholarshipService.searchAll(keyword, sort);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0,
                list.size(),
                1,
                list.size(),
                false,
                false
        );

        return ResponseEntity.ok(new PageResponse<>(list, pageInfo));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ScholarshipResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scholarshipService.getById(id));
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
