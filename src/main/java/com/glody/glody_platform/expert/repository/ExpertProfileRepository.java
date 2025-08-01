package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.ExpertProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {

    /**
     * Chỉ lấy những profile whose user has roleName = :roleName
     */
    Page<ExpertProfile> findAllByUser_Roles_RoleName(String roleName, Pageable pageable);

    /**
     * Search by keyword + filter by roleName
     */
    @Query("""
      SELECT e FROM ExpertProfile e
      JOIN e.user u
      JOIN u.roles r
      WHERE r.roleName = :roleName
        AND (
          LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(e.expertise) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
      """)
    Page<ExpertProfile> searchByKeyword(
            @Param("roleName") String roleName,
            @Param("keyword")   String keyword,
            Pageable pageable
    );

    /**
     * (Nếu dùng advanced search theo country/minYears)
     */
    @Query("""
      SELECT e FROM ExpertProfile e
      JOIN e.user u
      JOIN u.roles r
      LEFT JOIN e.advisingCountries c
      WHERE r.roleName = :roleName
        AND (:country IS NULL OR c.name = :country)
        AND (:minYears IS NULL OR e.yearsOfExperience >= :minYears)
        AND (:keyword IS NULL OR
             LOWER(e.expertise) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
      """)
    Page<ExpertProfile> searchExperts(
            @Param("roleName") String roleName,
            @Param("country")  String country,
            @Param("minYears") Integer minYears,
            @Param("keyword")  String keyword,
            Pageable pageable
    );

    /** Lấy một expert profile theo userId */
    Optional<ExpertProfile> findByUserId(Long userId);
}
