package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.ScholarshipRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipRequirementRepository extends JpaRepository<ScholarshipRequirement, Long> {
    List<ScholarshipRequirement> findByScholarshipIdAndIsDeletedFalse(Long scholarshipId);
}
