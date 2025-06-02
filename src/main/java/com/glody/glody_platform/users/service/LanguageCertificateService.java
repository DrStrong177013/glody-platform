package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.LanguageCertificateRequest;
import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
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

    /**
     * Lấy danh sách chứng chỉ theo ID hồ sơ người dùng.
     *
     * @param profileId ID hồ sơ
     * @return Danh sách chứng chỉ
     */
    public List<LanguageCertificateResponse> getCertificates(Long profileId) {
        return certificateRepository.findByProfileId(profileId)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Thêm chứng chỉ mới vào hồ sơ người dùng.
     *
     * @param profileId ID hồ sơ
     * @param request   Dữ liệu chứng chỉ
     */
    public void addCertificate(Long profileId, LanguageCertificateRequest request) {
        UserProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        LanguageCertificate cert = new LanguageCertificate();
        cert.setProfile(profile);
        cert.setCertificateName(request.getCertificateName());
        cert.setSkill(request.getSkill());
        cert.setScore(request.getScore());
        cert.setResultLevel(request.getResultLevel());

        certificateRepository.save(cert);
    }
    public void updateCertificate(Long certificateId, LanguageCertificateRequest request) {
        LanguageCertificate cert = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        cert.setCertificateName(request.getCertificateName());
        cert.setSkill(request.getSkill());
        cert.setScore(request.getScore());
        cert.setResultLevel(request.getResultLevel());

        certificateRepository.save(cert);
    }

    /**
     * Xóa chứng chỉ theo ID.
     *
     * @param certId ID chứng chỉ
     */
    public void deleteCertificate(Long certId) {
        certificateRepository.deleteById(certId);
    }

    /**
     * Convert Entity → Response DTO
     */
    private LanguageCertificateResponse toResponseDto(LanguageCertificate cert) {
        return LanguageCertificateResponse.builder()
                .id(cert.getId())
                .certificateName(cert.getCertificateName())
                .skill(cert.getSkill())
                .score(cert.getScore())
                .resultLevel(cert.getResultLevel())
                .build();
    }

}
