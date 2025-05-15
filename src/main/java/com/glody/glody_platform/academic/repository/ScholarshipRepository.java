package com.glody.glody_platform.academic.repository;

import com.glody.glody_platform.academic.entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByUniversityId(Long universityId);
}
