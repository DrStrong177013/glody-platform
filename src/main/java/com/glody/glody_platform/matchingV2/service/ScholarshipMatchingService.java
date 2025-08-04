package com.glody.glody_platform.matchingV2.service;

import com.glody.glody_platform.matchingV2.dto.*;
import com.glody.glody_platform.matchingV2.criteria.ScholarshipSearchCriteria;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.matchingV2.strategy.schoolar.impl.*;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.university.repository.ScholarshipRepository;
import com.glody.glody_platform.university.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipMatchingService {

    private final UserProfileRepository userProfileRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final SchoolRepository schoolRepository;

    // Inject các strategy
    private final GpaScoringSchoolarshipStrategy gpaStrategy;
    private final LanguageScoringSchoolarshipStrategy languageStrategy;
    private final MajorScoringSchoolarshipStrategy majorStrategy;
    private final CountryScoringSchoolarshipStrategy countryStrategy;

    public ScholarshipSearchResponse matchScholarships(ScholarshipSearchCriteria criteria) {
        // 1. Lấy profile user
        UserProfileDto profile = userProfileRepository.findDtoByUserId(criteria.getUserId());
        if (profile == null) {
            throw new RuntimeException("Không tìm thấy hồ sơ người dùng");
        }

        // 2. Lấy danh sách học bổng
        List<Scholarship> scholarships = scholarshipRepository.findAllActive();

        // 3. Chuẩn bị kết quả
        List<ScholarshipDto> resultDtos = new ArrayList<>();
        int matchedCount = 0;
        double totalMatch = 0.0;

        for (Scholarship scholarship : scholarships) {
            // Lấy trường học liên quan (nếu có)
            School school = scholarship.getSchool();

            // Tạo Map custom param (ưu tiên filter nếu truyền vào)
            Map<String, Object> customCriteria = new HashMap<>();
            if (criteria.getGpa() != null) customCriteria.put("gpa", criteria.getGpa());
            if (criteria.getMajor() != null) customCriteria.put("major", criteria.getMajor());
            if (criteria.getTargetCountry() != null) customCriteria.put("targetCountry", criteria.getTargetCountry());
            if (criteria.getLanguageCertificate() != null) customCriteria.put("languageCertificate", criteria.getLanguageCertificate());
            if (criteria.getLanguageScore() != null) customCriteria.put("languageScore", criteria.getLanguageScore());
            if (school != null && school.getCountry() != null) customCriteria.put("schoolCountry", school.getCountry().getName());

            // Chấm điểm từng tiêu chí
            Map<String, Integer> criteriaScores = new HashMap<>();
            List<String> missingRequirements = new ArrayList<>();
            List<String> recommendations = new ArrayList<>();

            // GPA
            int gpaScore = -1;
            // Ngôn ngữ
            int langScore = -1;
            // Ngành
            int majorScore = -1;

            // Học bổng không có điều kiện thì luôn pass
            if (scholarship.getConditions() != null && !scholarship.getConditions().isEmpty()) {
                for (String condition : scholarship.getConditions()) {
                    // GPA
                    ScoringResult gpaResult = gpaStrategy.score(profile, condition, customCriteria);
                    if (gpaResult.getScore() != -1) {
                        gpaScore = gpaResult.getScore();
                        if (gpaResult.getScore() == 0) {
                            missingRequirements.add("GPA");
                            if (gpaResult.getReason() != null) recommendations.add(gpaResult.getReason());
                        }
                    }

                    // Language
                    ScoringResult langResult = languageStrategy.score(profile, condition, customCriteria);
                    if (langResult.getScore() != -1) {
                        langScore = langResult.getScore();
                        if (langResult.getScore() == 0) {
                            missingRequirements.add("LANGUAGE");
                            if (langResult.getReason() != null) recommendations.add(langResult.getReason());
                        }
                    }

                    // Major
                    ScoringResult majorResult = majorStrategy.score(profile, condition, customCriteria);
                    if (majorResult.getScore() != -1) {
                        majorScore = majorResult.getScore();
                        if (majorResult.getScore() == 0) {
                            missingRequirements.add("MAJOR");
                            if (majorResult.getReason() != null) recommendations.add(majorResult.getReason());
                        }
                    }
                }
            }

            // Country (từ school, không phải trong điều kiện)
            int countryScore = -1;
            if (school != null) {
                ScoringResult countryResult = countryStrategy.score(profile, "", customCriteria);
                countryScore = countryResult.getScore();
                if (countryScore == 0) {
                    missingRequirements.add("COUNTRY");
                    if (countryResult.getReason() != null) recommendations.add(countryResult.getReason());
                }
            }

            // Map criteriaScores
            criteriaScores.put("GPA", gpaScore == -1 ? 1 : gpaScore); // Nếu không có điều kiện, mặc định pass
            criteriaScores.put("LANGUAGE", langScore == -1 ? 1 : langScore);
            criteriaScores.put("MAJOR", majorScore == -1 ? 1 : majorScore);
            criteriaScores.put("COUNTRY", countryScore == -1 ? 1 : countryScore);

            // Tính điểm (giả sử trọng số đã cố định, có thể lấy động nếu bạn lưu trong DB)
            double matchPercentage =
                    criteriaScores.get("GPA") * 0.4 +
                            criteriaScores.get("LANGUAGE") * 0.2 +
                            criteriaScores.get("MAJOR") * 0.2 +
                            criteriaScores.get("COUNTRY") * 0.2;

            matchPercentage = matchPercentage * 100.0;

            // Tính matchedCount
            if (matchPercentage >= 60) matchedCount++;
            totalMatch += matchPercentage;

            // Build ScholarshipDto
            ScholarshipDto dto = new ScholarshipDto();
            dto.setId(scholarship.getId());
            dto.setTitle(scholarship.getTitle());
            dto.setSponsor(scholarship.getSponsor());
            dto.setValue(scholarship.getValue());
            dto.setApplicationDeadline(scholarship.getApplicationDeadline() != null ? scholarship.getApplicationDeadline().toString() : null);
            dto.setSchoolName(school != null ? school.getName() : null);
            dto.setMatchPercentage(matchPercentage);
            dto.setCriteriaScores(criteriaScores);
            dto.setMissingRequirements(missingRequirements);
            dto.setDetailsUrl("/scholarships/" + scholarship.getId());
            dto.setRecommendations(recommendations);

            resultDtos.add(dto);
        }



        // Build summary
        MatchingSummary summary = new MatchingSummary();
        summary.setTotals(scholarships.size());
        summary.setMatchedCount(matchedCount);
        summary.setAverageMatch(scholarships.size() > 0 ? totalMatch / scholarships.size() : 0);

        ScholarshipSearchResponse response = new ScholarshipSearchResponse();
        response.setSummary(summary);
        response.setScholarships(resultDtos);
        response.setRecommendations(Collections.singletonList("Cập nhật hồ sơ để tăng tỷ lệ phù hợp."));

        return response;
    }

    public List<ScholarshipLightDto> matchScholarshipsLight(ScholarshipSearchCriteria criteria) {
        // 1. Lấy profile user
        UserProfileDto profile = userProfileRepository.findDtoByUserId(criteria.getUserId());
        if (profile == null) {
            throw new RuntimeException("Không tìm thấy hồ sơ người dùng");
        }

        // 2. Lấy danh sách học bổng
        List<Scholarship> scholarships = scholarshipRepository.findAllActive();
        List<ScholarshipLightDto> resultDtos = new ArrayList<>();

        for (Scholarship scholarship : scholarships) {
            School school = scholarship.getSchool();

            Map<String, Object> customCriteria = new HashMap<>();
            if (criteria.getGpa() != null) customCriteria.put("gpa", criteria.getGpa());
            if (criteria.getMajor() != null) customCriteria.put("major", criteria.getMajor());
            if (criteria.getTargetCountry() != null) customCriteria.put("targetCountry", criteria.getTargetCountry());
            if (criteria.getLanguageCertificate() != null) customCriteria.put("languageCertificate", criteria.getLanguageCertificate());
            if (criteria.getLanguageScore() != null) customCriteria.put("languageScore", criteria.getLanguageScore());
            if (school != null && school.getCountry() != null)
                customCriteria.put("schoolCountry", school.getCountry().getName());

            // Matching
            int gpaScore = -1, langScore = -1, majorScore = -1, countryScore = -1;

            // Chấm điểm từng điều kiện, giống logic cũ
            if (scholarship.getConditions() != null && !scholarship.getConditions().isEmpty()) {
                for (String condition : scholarship.getConditions()) {
                    // GPA
                    ScoringResult gpaResult = gpaStrategy.score(profile, condition, customCriteria);
                    if (gpaResult.getScore() != -1) gpaScore = gpaResult.getScore();

                    // Language
                    ScoringResult langResult = languageStrategy.score(profile, condition, customCriteria);
                    if (langResult.getScore() != -1) langScore = langResult.getScore();

                    // Major
                    ScoringResult majorResult = majorStrategy.score(profile, condition, customCriteria);
                    if (majorResult.getScore() != -1) majorScore = majorResult.getScore();
                }
            }
            // Country (từ school)
            if (school != null) {
                ScoringResult countryResult = countryStrategy.score(profile, "", customCriteria);
                countryScore = countryResult.getScore();
            }

            // Nếu không có điều kiện, mặc định pass (score = 1)
            gpaScore = (gpaScore == -1) ? 1 : gpaScore;
            langScore = (langScore == -1) ? 1 : langScore;
            majorScore = (majorScore == -1) ? 1 : majorScore;
            countryScore = (countryScore == -1) ? 1 : countryScore;

            // Trọng số (có thể tùy chỉnh, ví dụ 40-20-20-20)
            double matchPercentage =
                    gpaScore * 0.4 +
                            langScore * 0.2 +
                            majorScore * 0.2 +
                            countryScore * 0.2;
            matchPercentage = matchPercentage * 100.0;

            // Build DTO "light"
            ScholarshipLightDto dto = new ScholarshipLightDto();
            dto.setId(scholarship.getId());
            dto.setTitle(scholarship.getTitle());
            dto.setSchoolName(school != null ? school.getName() : null);

            // Nếu có tuitionFee (nếu không, dùng value)
            // dto.setTuitionFee(scholarship.getTuitionFee()); // Nếu có
            // Nếu không có tuitionFee, bạn có thể trả value (giá trị học bổng)
            dto.setTuitionFee(null); // hoặc dto.setTuitionFee(Double.parseDouble(scholarship.getValue())); nếu phù hợp

            dto.setMatchPercentage(matchPercentage);

            resultDtos.add(dto);
        }
        return resultDtos;
    }

}
