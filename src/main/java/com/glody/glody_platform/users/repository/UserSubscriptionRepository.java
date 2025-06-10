package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findTopByUserIdAndIsActiveTrueOrderByEndDateDesc(Long userId);

    List<UserSubscription> findAllByUserIdAndIsActiveTrue(Long userId);

    // Lấy tất cả gói chưa bị soft delete của 1 user
    List<UserSubscription> findAllByUserIdAndIsDeletedFalse(Long userId);

    // Lấy bản ghi đang active nhất (nếu có) của 1 user
    Optional<UserSubscription> findTopByUserIdAndIsDeletedFalseAndIsActiveTrueOrderByEndDateDesc(Long userId);

    // Lấy toàn bộ kể cả hết hạn, chỉ loại deleted
    List<UserSubscription> findAllByUserId(Long userId);

    // Lấy tất cả gói active (để xét tự động hết hạn)
    Page<UserSubscription> findByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
    Page<UserSubscription> findByUserIdAndIsDeletedFalseAndIsActive(Long userId, Boolean isActive, Pageable pageable);
    List<UserSubscription> findAllByUserIdAndIsDeletedFalseAndIsActive(Long userId, Boolean isActive);
    long countByIsDeletedFalse();
    long countByIsDeletedFalseAndIsActiveTrue();
    long countByIsDeletedFalseAndIsActiveFalse();
    long countByIsDeletedFalseAndSubscriptionPackage_Name(String name);
    @Query("SELECT us FROM UserSubscription us " +
            "WHERE us.user.id = :userId AND us.isActive = true " +
            "AND :date BETWEEN us.startDate AND us.endDate")
    Optional<UserSubscription> findActiveByUserId(@Param("userId") Long userId, @Param("date") LocalDate date);
    @Query ("SELECT s FROM UserSubscription s WHERE s.user.id = :userId AND s.startDate <= :date AND s.endDate >= :date ")
    List<UserSubscription> findAllActiveByUserId(Long userId, LocalDate date);



}