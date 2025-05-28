package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipRequirementRepository extends JpaRepository<ScholarshipRequirement, Long> {
    List<ScholarshipRequirement> findByScholarshipIdAndIsDeletedFalse(Long scholarshipId);
    List<ScholarshipRequirement> findByIsDeletedFalse();
    Page<ScholarshipRequirement> findByScholarshipIdAndIsDeletedFalse(Long scholarshipId, Pageable pageable);
    Page<ScholarshipRequirement> findByIsDeletedFalse(Pageable pageable);

    List<ScholarshipRequirement> findByScholarshipIdAndIsDeletedFalse(Long scholarshipId, Sort sort);
    List<ScholarshipRequirement> findByIsDeletedFalse(Sort sort);
}
