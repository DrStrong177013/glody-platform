package com.glody.glody_platform.partner.repository;

import com.glody.glody_platform.partner.entity.Partner;
import com.glody.glody_platform.partner.entity.PartnerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByIsDeletedFalse();
    List<Partner> findByCategoryAndIsDeletedFalse(PartnerCategory category);
}
