package com.glody.glody_platform.university.repository;

import com.glody.glody_platform.university.entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long>, JpaSpecificationExecutor<Scholarship> {
    @Query("SELECT s FROM Scholarship s LEFT JOIN FETCH s.school")
    List<Scholarship> findAllWithSchool();
    @Query("SELECT s FROM Scholarship s " +
            "JOIN FETCH s.school sc " +
            "LEFT JOIN FETCH sc.programs")
    List<Scholarship> findAllWithSchoolAndPrograms();
    @Query("SELECT s FROM Scholarship s WHERE s.deletedAt IS NULL AND s.isDeleted = false")
    List<Scholarship> findAllActive();
}
