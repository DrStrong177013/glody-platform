package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.matching.dto.ScholarshipMatchDto;
import com.glody.glody_platform.matching.entity.MatchingHistory;
import com.glody.glody_platform.matching.repository.MatchingHistoryRepository;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.repository.ScholarshipRepository;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipMatchingService {

    private final UserProfileRepository userProfileRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final MatchingHistoryRepository historyRepository;

    public List<ScholarshipMatchDto> suggestScholarships(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"));

        List<Scholarship> allScholarships = scholarshipRepository.findAllWithSchool();

        return allScholarships.stream()
                .map(scholarship -> {
                    int match = calculateMatch(profile, scholarship); // ⚠️ phải gọi trước

                    ScholarshipMatchDto dto = new ScholarshipMatchDto();
                    dto.setId(scholarship.getId());
                    dto.setTitle(scholarship.getTitle());
                    dto.setSponsor(scholarship.getSponsor());
                    dto.setValue(scholarship.getValue());
                    dto.setSchoolName(scholarship.getSchool() != null ? scholarship.getSchool().getName() : null);
                    dto.setSchoolLogoUrl(scholarship.getSchool() != null ? scholarship.getSchool().getLogoUrl() : null);
                    dto.setApplicationDeadline(scholarship.getApplicationDeadline());
                    dto.setMatchPercentage(match);

                    String conditionSummary = "";
                    if (scholarship.getConditions() != null && !scholarship.getConditions().isEmpty()) {
                        conditionSummary = String.join(", ", scholarship.getConditions());
                    }
                    dto.setHighlightedConditions(conditionSummary);

                    // 📦 Lưu lịch sử matching
                    MatchingHistory history = new MatchingHistory();
                    history.setUser(profile.getUser());
                    history.setReferenceId(scholarship.getId());
                    history.setMatchType("SCHOLARSHIP");
                    history.setMatchPercentage(match);
                    history.setAdditionalInfo(scholarship.getTitle());

                    historyRepository.save(history);

                    return dto;
                })
                .filter(dto -> dto.getApplicationDeadline() == null || !dto.getApplicationDeadline().isBefore(LocalDate.now()))
                .sorted((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()))
                .limit(10)
                .collect(Collectors.toList());
    }

    private int calculateMatch(UserProfile profile, Scholarship scholarship) {
        int totalScore = 0;

        // GPA matching (30%)
        if (profile.getGpa() != null) {
            double gpa = profile.getGpa();
            if (gpa >= 9.0) totalScore += 30;
            else if (gpa >= 8.0) totalScore += 25;
            else if (gpa >= 7.0) totalScore += 20;
            else if (gpa >= 6.0) totalScore += 10;
        }

        // Major matching (20%)
        if (profile.getMajor() != null && scholarship.getDescription() != null) {
            String major = profile.getMajor().toLowerCase();
            if (scholarship.getDescription().toLowerCase().contains(major)) {
                totalScore += 20;
            }
        }

        // Target Country matching (20%)
        if (profile.getTargetCountry() != null &&
                scholarship.getSchool() != null &&
                scholarship.getSchool().getLocation() != null) {
            String targetCountry = profile.getTargetCountry().toLowerCase();
            String schoolLocation = scholarship.getSchool().getLocation().toLowerCase();
            if (schoolLocation.contains(targetCountry)) {
                totalScore += 20;
            }
        }

        // Language certificate (20%)
        if (profile.getLanguageCertificates() != null && !profile.getLanguageCertificates().isEmpty()) {
            totalScore += 20;
        }

        // Extracurricular activities (10%)
        if (profile.getExtracurricularActivities() != null &&
                !profile.getExtracurricularActivities().isBlank()) {
            totalScore += 10;
        }

        return Math.min(totalScore, 100);
    }
}
