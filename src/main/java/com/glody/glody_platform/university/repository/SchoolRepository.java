package com.glody.glody_platform.university.repository;

import com.glody.glody_platform.university.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);
}
