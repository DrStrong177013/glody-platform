package com.glody.glody_platform.academic.repository;

import com.glody.glody_platform.academic.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByCountryId(Long countryId);
}
