package com.glody.glody_platform.academic.repository;

import com.glody.glody_platform.academic.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByName(String name); // Optional, useful for validation
}
