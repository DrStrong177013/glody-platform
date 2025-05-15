package com.glody.glody_platform.academic.repository;

import com.glody.glody_platform.academic.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByUniversityId(Long universityId);
}
