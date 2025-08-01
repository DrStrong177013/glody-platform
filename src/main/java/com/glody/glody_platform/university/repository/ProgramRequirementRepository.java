package com.glody.glody_platform.university.repository;

import com.glody.glody_platform.university.entity.ProgramRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRequirementRepository extends JpaRepository<ProgramRequirement, Long> {
    Optional<ProgramRequirement> findByProgramId(Long programId);
}
