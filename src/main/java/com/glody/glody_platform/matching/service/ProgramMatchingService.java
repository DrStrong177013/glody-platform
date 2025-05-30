package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.matching.dto.ProgramMatchDto;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramMatchingService {

    private final ProgramRepository programRepository;
    private final UserProfileRepository userProfileRepository;

    public List<ProgramMatchDto> matchPrograms(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"));

        List<Program> programs = programRepository.findAll();

        return programs.stream()
                .filter(program -> program.getSchool() != null) // loại bỏ dữ liệu thiếu
                .map(program -> {
                    ProgramMatchDto dto = new ProgramMatchDto();

                    dto.setId(program.getId());
                    dto.setSchoolName(program.getSchool().getName());
                    dto.setSchoolLogoUrl(program.getSchool().getLogoUrl());
                    dto.setLevel(program.getLevel().name());
                    dto.setLanguage(program.getLanguage().name());
                    dto.setMajors(program.getMajors());
                    dto.setTuitionFee(program.getTuitionFee());
                    dto.setLivingCost(program.getLivingCost());
                    dto.setDormFee(program.getDormFee());
                    dto.setScholarshipSupport(program.getScholarshipSupport());

                    int score = calculateMatchPercentage(profile, program);
                    dto.setMatchPercentage(score);

                    return dto;
                })
                .sorted((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()))
                .collect(Collectors.toList());
    }

    private int calculateMatchPercentage(UserProfile profile, Program program) {
        int score = 0;
        int total = 0;

        if (profile.getTargetCountry() != null && program.getSchool().getLocation() != null) {
            total += 10;
            if (program.getSchool().getLocation().equalsIgnoreCase(profile.getTargetCountry())) {
                score += 10;
            }
        }

        if (profile.getGpa() != null && program.getRequirement() != null && program.getRequirement().getGpaRequirement() != null) {
            total += 20;
            try {
                double requiredGpa = Double.parseDouble(program.getRequirement().getGpaRequirement().replaceAll("[^\\d.]", ""));
                if (profile.getGpa() >= requiredGpa) {
                    score += 20;
                }
            } catch (NumberFormatException ignored) {}
        }

        if (program.getMajors() != null && profile.getMajor() != null) {
            total += 20;
            boolean match = program.getMajors().stream()
                    .anyMatch(m -> m.toLowerCase().contains(profile.getMajor().toLowerCase()));
            if (match) score += 20;
        }

        if (program.getScholarshipSupport() != null && program.getScholarshipSupport()) {
            total += 10;
            score += 10;
        }

        if (total == 0) return 0;
        return (int) ((score / (double) total) * 100);
    }
}
