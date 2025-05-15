package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, Long> {
    Optional<SubscriptionPackage> findByName(String name);
}
