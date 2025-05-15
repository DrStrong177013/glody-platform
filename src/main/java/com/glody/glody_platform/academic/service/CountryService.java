package com.glody.glody_platform.academic.service;

import com.glody.glody_platform.academic.dto.CountryDto;
import com.glody.glody_platform.academic.entity.Country;
import com.glody.glody_platform.academic.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<CountryDto> getAll() {
        return countryRepository.findAll().stream().map(CountryDto::fromEntity).toList();
    }

    public void add(CountryDto dto) {
        countryRepository.save(dto.toEntity());
    }

    public void update(CountryDto dto) {
        countryRepository.save(dto.toEntity());
    }

    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}
