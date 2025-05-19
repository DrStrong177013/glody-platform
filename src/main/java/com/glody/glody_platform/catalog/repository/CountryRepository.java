package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Page<Country> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Pageable pageable);
}
