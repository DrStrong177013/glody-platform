package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.university.repository.ScholarshipRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileMatchingService {

    private final UserProfileRepository userProfileRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final ProgramRepository programRepository;

    @Transactional
    public List<Scholarship> matchScholarships(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        List<Scholarship> all = scholarshipRepository.findAll();

        return all.stream()
                .filter(s -> {
                    if (s.getConditions() == null) return true;
                    return s.getConditions().stream().allMatch(cond -> matchCondition(profile, cond));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Program> matchPrograms(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));

        DegreeLevel level = convertLevel(profile.getEducationLevel());

        return programRepository.findAll().stream()
                .filter(p -> Objects.equals(p.getLevel(), level))
                .filter(p -> Objects.equals(p.getLanguage().name(), getMainLanguage(profile)))
                .filter(p -> {
                    if (profile.getMajor() == null || profile.getMajor().isBlank()) return true;
                    return p.getMajors().stream()
                            .anyMatch(major -> major.toLowerCase().contains(profile.getMajor().toLowerCase()));
                })
                .collect(Collectors.toList());
    }

    private boolean matchCondition(UserProfile profile, String condition) {
        String lower = condition.toLowerCase();

        if (lower.contains("gpa")) {
            return profile.getGpa() != null && profile.getGpa() >= extractGpaThreshold(condition);
        }

        if (lower.contains("ielts") || lower.contains("toefl") || lower.contains("toeic") || lower.contains("chinese")) {
            return profile.getLanguageCertificates().stream()
                    .anyMatch(cert -> condition.toLowerCase().contains(cert.getCertificateName().toLowerCase()));
        }

        return true;
    }

    private double extractGpaThreshold(String condition) {
        try {
            String[] parts = condition.replace(",", ".").split("[^0-9.]");
            return Arrays.stream(parts)
                    .filter(p -> !p.isBlank())
                    .mapToDouble(Double::parseDouble)
                    .findFirst().orElse(0);
        } catch (Exception e) {
            return 0;
        }
    }

    private String getMainLanguage(UserProfile profile) {
        return profile.getLanguageCertificates().stream()
                .map(LanguageCertificate::getCertificateName)
                .map(String::toUpperCase)
                .anyMatch(s -> s.contains("TOEFL") || s.contains("IELTS") || s.contains("ENGLISH")) ? "ENGLISH" : "CHINESE";
    }

    private DegreeLevel convertLevel(String educationLevel) {
        if (educationLevel == null) return DegreeLevel.CU_NHAN;
        return switch (educationLevel.trim().toUpperCase()) {
            case "THẠC SĨ", "THAC SI", "MASTER" -> DegreeLevel.THAC_SI;
            case "TIẾN SĨ", "TIEN SI", "DOCTOR" -> DegreeLevel.TIEN_SI;
            default -> DegreeLevel.CU_NHAN;
        };
    }
}
