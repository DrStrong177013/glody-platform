package com.glody.glody_platform.academic.service;

import com.glody.glody_platform.academic.dto.UniversityDto;
import com.glody.glody_platform.academic.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public List<UniversityDto> getByCountry(Long countryId) {
        return universityRepository.findByCountryId(countryId).stream().map(UniversityDto::fromEntity).toList();
    }

    public void add(UniversityDto dto) {
        universityRepository.save(dto.toEntity());
    }

    public void update(UniversityDto dto) {
        universityRepository.save(dto.toEntity());
    }

    public void delete(Long id) {
        universityRepository.deleteById(id);
    }
}
