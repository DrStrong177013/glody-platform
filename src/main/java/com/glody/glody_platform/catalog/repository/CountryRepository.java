package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Sort sort);
    Page<Country> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Pageable pageable);

}
