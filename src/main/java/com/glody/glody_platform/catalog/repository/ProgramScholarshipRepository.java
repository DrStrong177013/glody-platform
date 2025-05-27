package com.glody.glody_platform.catalog.repository;

import com.glody.glody_platform.catalog.entity.ProgramScholarship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramScholarshipRepository extends JpaRepository<ProgramScholarship, Long> {

    // Lấy tất cả học bổng theo chương trình
    List<ProgramScholarship> findByProgramId(Long programId);

    // Lấy tất cả chương trình theo học bổng
    List<ProgramScholarship> findByScholarshipId(Long scholarshipId);

    // Xoá toàn bộ liên kết theo học bổng
    void deleteByScholarshipId(Long scholarshipId);

    // Optional: Xoá theo programId (nếu sau này cần clear)
    void deleteByProgramId(Long programId);

    // Optional: Kiểm tra tồn tại
    boolean existsByProgramIdAndScholarshipId(Long programId, Long scholarshipId);
}
