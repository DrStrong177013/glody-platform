package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {
    Optional<ExpertProfile> findByUser(User user);
    @Query("SELECT e FROM ExpertProfile e JOIN e.user u " +
            "WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.expertise) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ExpertProfile> searchExperts(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
    SELECT e FROM ExpertProfile e
    JOIN e.user u
    LEFT JOIN e.advisingCountries c
    WHERE (:country IS NULL OR c.name = :country)
    AND (:minYears IS NULL OR e.yearsOfExperience >= :minYears)
    AND (:keyword IS NULL OR 
         LOWER(e.expertise) LIKE LOWER(CONCAT('%', :keyword, '%')) 
         OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<ExpertProfile> searchExperts(
            @Param("country") String country,
            @Param("minYears") Integer minYears,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
