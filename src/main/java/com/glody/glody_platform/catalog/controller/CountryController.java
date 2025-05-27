package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.CountryRequestDto;
import com.glody.glody_platform.catalog.dto.CountryResponseDto;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.service.CountryService;
import com.glody.glody_platform.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

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
            Page<CountryResponseDto> paged = countryService.searchPagedDto(keyword, pageable);

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

        List<CountryResponseDto> list = countryService.searchAllDto(keyword, sort);
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
    public Country create(@RequestBody CountryRequestDto dto) {
        return countryService.create(dto);
    }

    @PutMapping("/{id}")
    public Country update(@PathVariable Long id, @RequestBody CountryRequestDto dto) {
        return countryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        countryService.softDelete(id);
        return ResponseEntity.ok("Country soft deleted successfully");
    }
}
