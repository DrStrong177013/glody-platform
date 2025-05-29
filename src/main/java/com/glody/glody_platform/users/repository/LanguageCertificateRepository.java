package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.LanguageCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageCertificateRepository extends JpaRepository<LanguageCertificate, Long> {
    List<LanguageCertificate> findByProfileId(Long profileId);
}
