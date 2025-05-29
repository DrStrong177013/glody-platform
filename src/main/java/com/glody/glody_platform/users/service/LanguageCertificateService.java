package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.LanguageCertificateDto;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.LanguageCertificateRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageCertificateService {

    private final LanguageCertificateRepository certificateRepository;
    private final UserProfileRepository profileRepository;

    public List<LanguageCertificateDto> getCertificates(Long profileId) {
        return certificateRepository.findByProfileId(profileId)
                .stream()
                .map(cert -> {
                    LanguageCertificateDto dto = new LanguageCertificateDto();
                    dto.setCertificateName(cert.getCertificateName());
                    dto.setSkill(cert.getSkill());
                    dto.setScore(cert.getScore());
                    dto.setResultLevel(cert.getResultLevel());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void addCertificate(Long profileId, LanguageCertificateDto dto) {
        UserProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        LanguageCertificate cert = new LanguageCertificate();
        cert.setProfile(profile);
        cert.setCertificateName(dto.getCertificateName());
        cert.setSkill(dto.getSkill());
        cert.setScore(dto.getScore());
        cert.setResultLevel(dto.getResultLevel());

        certificateRepository.save(cert);
    }

    public void deleteCertificate(Long certId) {
        certificateRepository.deleteById(certId);
    }
}
