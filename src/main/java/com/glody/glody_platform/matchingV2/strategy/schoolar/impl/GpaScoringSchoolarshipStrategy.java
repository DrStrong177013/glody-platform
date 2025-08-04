package com.glody.glody_platform.matchingV2.strategy.schoolar.impl;

import com.glody.glody_platform.matchingV2.dto.ScoringResult;
import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.matchingV2.strategy.schoolar.ScoringSchoolarStrategy;
import com.glody.glody_platform.users.dto.UserProfileDto;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class GpaScoringSchoolarshipStrategy implements ScoringSchoolarStrategy {

    @Override
    public ScoringResult score(UserProfileDto userProfile, String conditionText, Map<String, Object> customCriteria) {
        Double userGpa = customCriteria.get("gpa") != null ? (Double) customCriteria.get("gpa") : userProfile.getGpa();

        if (conditionText != null && conditionText.toUpperCase().contains("GPA")) {
            double requiredGpa = Double.parseDouble(conditionText.replaceAll("[^0-9.]", ""));
            if (userGpa >= requiredGpa) {
                return new ScoringResult(1, null, null);
            } else {
                String reason = "GPA của bạn (" + userGpa + ") chưa đạt yêu cầu (" + requiredGpa + ")";
                String rec = "Cần cải thiện GPA hoặc chọn học bổng phù hợp hơn";
                return new ScoringResult(0, reason, rec);
            }
        }
        // Không phải điều kiện GPA
        return new ScoringResult(-1, null, null); // -1: strategy này không áp dụng cho condition này
    }
}
