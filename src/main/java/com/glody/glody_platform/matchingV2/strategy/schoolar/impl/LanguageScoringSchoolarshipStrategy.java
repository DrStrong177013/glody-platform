package com.glody.glody_platform.matchingV2.strategy.schoolar.impl;

import com.glody.glody_platform.matchingV2.dto.ScoringResult;
import com.glody.glody_platform.matchingV2.strategy.schoolar.ScoringSchoolarStrategy;
import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.dto.LanguageCertificateDto; // giả định bạn có DTO này
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class LanguageScoringSchoolarshipStrategy implements ScoringSchoolarStrategy {

    @Override
    public ScoringResult score(UserProfileDto userProfile, String conditionText, Map<String, Object> customCriteria) {
        String certType = null;
        Double requiredScore = null;
        if (conditionText != null && (conditionText.toUpperCase().contains("IELTS") || conditionText.toUpperCase().contains("TOEFL"))) {
            certType = conditionText.split("≥")[0].trim().toUpperCase();
            requiredScore = Double.parseDouble(conditionText.replaceAll("[^0-9.]", ""));
        } else {
            return new ScoringResult(-1, null, null); // Không phải điều kiện ngôn ngữ
        }

        // Ưu tiên customCriteria nếu có
        String userCert = customCriteria.get("languageCertificate") != null
                ? (String) customCriteria.get("languageCertificate")
                : null;
        Double userScore = customCriteria.get("languageScore") != null
                ? (Double) customCriteria.get("languageScore")
                : null;

        // Nếu không có trong customCriteria thì lấy từ profile (tìm trong list certificates)
        if (userCert == null || userScore == null) {
            List<LanguageCertificateDto> certificates = userProfile.getLanguageCertificates();
            if (certificates != null) {
                for (LanguageCertificateDto cert : certificates) {
                    if (cert.getCertificateName() != null && cert.getCertificateName().toUpperCase().contains(certType)) {
                        userCert = cert.getCertificateName();
                        userScore = cert.getScore();
                        break;
                    }
                }
            }
        }

        if (userCert != null && userCert.toUpperCase().contains(certType)) {
            if (userScore != null && userScore >= requiredScore) {
                return new ScoringResult(1, null, null);
            } else {
                String reason = "Điểm " + certType + " của bạn (" + userScore + ") chưa đạt yêu cầu (" + requiredScore + ")";
                return new ScoringResult(0, reason, "Cần cải thiện điểm " + certType);
            }
        } else {
            String reason = "Bạn chưa có chứng chỉ " + certType;
            return new ScoringResult(0, reason, "Nên bổ sung chứng chỉ " + certType);
        }
    }
}
