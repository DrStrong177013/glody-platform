package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.matching.dto.ProgramMatchDto;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramMatchingService {

    private final ProgramRepository programRepository;
    private final UserProfileRepository userProfileRepository;
    private static final Logger log = LoggerFactory.getLogger(ProgramMatchingService.class);

    public List<ProgramMatchDto> findSuitablePrograms(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"));

        List<Program> programs = programRepository.findAll();

        return programs.stream()
                .filter(program -> isSuitable(profile, program)) // chỉ lấy chương trình phù hợp
                .map(program -> toDto(program)) // chuyển sang DTO
                .sorted((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()))
                .collect(Collectors.toList());
    }

    private boolean isSuitable(UserProfile profile, Program program) {
        if (program.getSchool() == null || program.getRequirement() == null) return false;

        // 1. Kiểm tra quốc gia mục tiêu
        if (profile.getTargetCountry() != null && program.getSchool().getLocation() != null) {
            if (!program.getSchool().getLocation().equalsIgnoreCase(profile.getTargetCountry())) {
                log.info("Program {} bị loại do khác quốc gia: {}, cần {}",
                        program.getId(), program.getSchool().getLocation(), profile.getTargetCountry());
                return false;
            }
        }

        // 2. Kiểm tra GPA
        if (profile.getGpa() != null && program.getRequirement().getGpaRequirement() != null) {
            try {
                double requiredGpa = extractGpa(program.getRequirement().getGpaRequirement());
                if (profile.getGpa() < requiredGpa) {
                    log.info("Program {} bị loại do GPA thấp hơn yêu cầu: {}, cần >= {}",
                            program.getId(), profile.getGpa(), requiredGpa);
                    return false;
                }
            } catch (NumberFormatException e) {
                log.warn("Không thể parse GPA requirement cho chương trình {}: {}",
                        program.getId(), program.getRequirement().getGpaRequirement());
                return false;
            }
        }

        // 3. Kiểm tra ngành học
        if (program.getMajors() != null && profile.getMajor() != null) {
            boolean match = program.getMajors().stream()
                    .anyMatch(m -> m.toLowerCase().contains(profile.getMajor().toLowerCase()));
            if (!match) {
                log.info("Program {} bị loại do ngành học không khớp: {}", program.getId(), profile.getMajor());
                return false;
            }
        }

        // 4. Kiểm tra chứng chỉ ngôn ngữ
        if (program.getRequirement().getLanguageRequirement() != null) {
            String requiredCert = extractRequiredLanguageType(program.getRequirement().getLanguageRequirement());
            double requiredScore = extractRequiredLanguageScore(program.getRequirement().getLanguageRequirement());

            boolean passed = profile.getLanguageCertificates() != null &&
                    profile.getLanguageCertificates().stream()
                            .filter(cert -> cert.getCertificateName().equalsIgnoreCase(requiredCert))
                            .mapToDouble(cert -> cert.getScore())
                            .average()
                            .orElse(0.0) >= requiredScore;

            if (!passed) {
                log.info("Program {} bị loại do không đủ điểm ngôn ngữ yêu cầu {} >= {}",
                        program.getId(), requiredCert, requiredScore);
                return false;
            }
        }

        return true;
    }


    private double extractGpa(String gpaRequirement) {
        // Lấy giá trị GPA chuẩn hóa (ưu tiên 4.0 nếu có)
        if (gpaRequirement.contains("3.0/4")) return 3.0;
        if (gpaRequirement.contains("4.0/4")) return 4.0;

        // fallback: parse số đầu tiên
        String[] tokens = gpaRequirement.split("[^\\d\\.]+");
        for (String token : tokens) {
            if (!token.isBlank()) {
                return Double.parseDouble(token);
            }
        }
        throw new NumberFormatException("Không thể parse GPA");
    }

    private String extractRequiredLanguageType(String requirement) {
        if (requirement.toUpperCase().contains("IELTS")) return "IELTS";
        if (requirement.toUpperCase().contains("TOEFL")) return "TOEFL";
        return "UNKNOWN";
    }

    private double extractRequiredLanguageScore(String requirement) {
        // Tìm điểm đầu tiên có dạng số
        String[] tokens = requirement.split("[^\\d\\.]+");
        for (String token : tokens) {
            if (!token.isBlank()) return Double.parseDouble(token);
        }
        throw new NumberFormatException("Không thể parse điểm chứng chỉ tiếng Anh");
    }


    private ProgramMatchDto toDto(Program program) {
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
        dto.setMatchPercentage(100); // Vì đã lọc trước nên mặc định phù hợp hoàn toàn
        return dto;
    }
}

