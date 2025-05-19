package com.glody.glody_platform.catalog.service;

import com.glody.glody_platform.catalog.dto.CountryRequestDto;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Page<Country> search(String keyword, Pageable pageable) {
        return countryRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(keyword, pageable);
    }

    public Country create(CountryRequestDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        return countryRepository.save(country);
    }

    public Country update(Long id, CountryRequestDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        country.setName(dto.getName());
        return countryRepository.save(country);
    }

    public void softDelete(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        country.setIsDeleted(true);
        country.setDeletedAt(LocalDateTime.now());
        countryRepository.save(country);
    }
}
