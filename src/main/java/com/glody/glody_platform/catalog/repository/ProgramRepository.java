package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Page<Program> findByIsDeletedFalseAndNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Program> findByIsDeletedFalse();

}
