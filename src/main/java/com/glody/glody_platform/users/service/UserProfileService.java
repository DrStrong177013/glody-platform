package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.LanguageCertificateDto;
import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.LanguageCertificateRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final LanguageCertificateRepository certificateRepository;

    public UserProfileDto getProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserProfileDto dto = new UserProfileDto();
        dto.setFullName(profile.getFullName());
        dto.setNationality(profile.getNationality());
        dto.setEducationLevel(profile.getEducationLevel());
        dto.setMajor(profile.getMajor());
        dto.setTargetCountry(profile.getTargetCountry());
        dto.setTargetYear(profile.getTargetYear());
        dto.setGpa(profile.getGpa());
        dto.setAvatarUrl(profile.getAvatarUrl());


        // Mapping language certificates
        List<LanguageCertificateDto> certificateDtos = profile.getLanguageCertificates()
                .stream()
                .map(cert -> {
                    LanguageCertificateDto certDto = new LanguageCertificateDto();
                    certDto.setCertificateName(cert.getCertificateName());
                    certDto.setSkill(cert.getSkill());
                    certDto.setScore(cert.getScore());
                    certDto.setResultLevel(cert.getResultLevel());
                    return certDto;
                }).toList();

        dto.setLanguageCertificates(certificateDtos);
        return dto;
    }

    @Transactional
    public void saveOrUpdate(Long userId, UserProfileDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(new UserProfile());
        profile.setUser(user);

        profile.setFullName(dto.getFullName());
        profile.setNationality(dto.getNationality());
        profile.setEducationLevel(dto.getEducationLevel());
        profile.setMajor(dto.getMajor());
        profile.setTargetCountry(dto.getTargetCountry());
        profile.setTargetYear(dto.getTargetYear());
        profile.setGpa(dto.getGpa());
        profile.setAvatarUrl(dto.getAvatarUrl());
        user.setAvatarUrl(dto.getAvatarUrl());

        // Xoá chứng chỉ cũ nếu đang cập nhật
        certificateRepository.deleteAll(profile.getLanguageCertificates());
        profile.getLanguageCertificates().clear();

        if (dto.getLanguageCertificates() != null) {
            for (LanguageCertificateDto certDto : dto.getLanguageCertificates()) {
                LanguageCertificate cert = new LanguageCertificate();
                cert.setProfile(profile);
                cert.setCertificateName(certDto.getCertificateName());
                cert.setSkill(certDto.getSkill());
                cert.setScore(certDto.getScore());
                cert.setResultLevel(certDto.getResultLevel());
                profile.getLanguageCertificates().add(cert);
            }
        }

        userProfileRepository.save(profile);
    }
}
