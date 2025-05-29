package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.ProgramRequestDto;
import com.glody.glody_platform.catalog.dto.ProgramResponseDto;
import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.service.ProgramService;
import com.glody.glody_platform.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

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
            Page<ProgramResponseDto> paged = programService.searchPaged(keyword, pageable);

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

        List<ProgramResponseDto> list = programService.searchAll(keyword, sort);
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
    public ResponseEntity<ProgramResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getById(id));
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
