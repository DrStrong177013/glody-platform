package com.glody.glody_platform.academic.controller;

import com.glody.glody_platform.academic.dto.CountryDto;
import com.glody.glody_platform.academic.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid CountryDto dto) {
        countryService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CountryDto dto) {
        dto.setId(id);
        countryService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

