package com.glody.glody_platform.partner.repository;

import com.glody.glody_platform.partner.entity.Partner;
import com.glody.glody_platform.partner.entity.PartnerClick;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerClickRepository extends JpaRepository<PartnerClick, Long> {
    List<PartnerClick> findByUser(User user);
    List<PartnerClick> findByPartner(Partner partner);
}
