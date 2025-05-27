package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    Page<Scholarship> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Scholarship> findByIsDeletedFalseAndMinGpaLessThanEqual(Double gpa);
    List<Scholarship> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Sort sort);
}
