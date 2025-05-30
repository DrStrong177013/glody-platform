package com.glody.glody_platform.university.repository;

import com.glody.glody_platform.university.entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long>, JpaSpecificationExecutor<Scholarship> {
}
