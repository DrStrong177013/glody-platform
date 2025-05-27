package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.UniversityRequestDto;
import com.glody.glody_platform.catalog.dto.UniversityResponseDto;
import com.glody.glody_platform.catalog.service.UniversityService;
import com.glody.glody_platform.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

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
            Page<UniversityResponseDto> paged = universityService.searchPaged(keyword, pageable);

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

        List<UniversityResponseDto> list = universityService.searchAll(keyword, sort);
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UniversityRequestDto dto) {
        return ResponseEntity.ok(universityService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UniversityRequestDto dto) {
        return ResponseEntity.ok(universityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        universityService.softDelete(id);
        return ResponseEntity.ok("University soft deleted successfully");
    }
}
