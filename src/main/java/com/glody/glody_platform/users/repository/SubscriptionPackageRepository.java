package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, Long> {
    Optional<SubscriptionPackage> findByName(String name);

    List<SubscriptionPackage> findAll(Sort sort);
    Page<SubscriptionPackage> findAll(Pageable pageable);
}