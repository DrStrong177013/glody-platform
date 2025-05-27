package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Sort sort);
    Page<University> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<University> findByIsDeletedFalse();
}
