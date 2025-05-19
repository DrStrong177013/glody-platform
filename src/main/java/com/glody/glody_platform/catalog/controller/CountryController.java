package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.CountryRequestDto;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public Page<Country> search(@RequestParam(defaultValue = "") String keyword, Pageable pageable) {
        return countryService.search(keyword, pageable);
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
