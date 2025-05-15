package com.glody.glody_platform.academic.repository;

import com.glody.glody_platform.academic.entity.ScholarshipRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipRequirementRepository extends JpaRepository<ScholarshipRequirement, Long> {
    List<ScholarshipRequirement> findByScholarshipId(Long scholarshipId);
}
