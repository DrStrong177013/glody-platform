package com.glody.glody_platform.matchingV2.strategy.schoolar.impl;


import com.glody.glody_platform.matchingV2.dto.ScoringResult;
import com.glody.glody_platform.matchingV2.strategy.schoolar.ScoringSchoolarStrategy;
import com.glody.glody_platform.users.dto.UserProfileDto;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class MajorScoringSchoolarshipStrategy implements ScoringSchoolarStrategy {

    @Override
    public ScoringResult score(UserProfileDto userProfile, String conditionText, Map<String, Object> customCriteria) {
        String userMajor = customCriteria.get("major") != null
                ? (String) customCriteria.get("major")
                : userProfile.getMajor();

        if (conditionText != null && conditionText.toLowerCase().contains("ngành")) {
            String[] parts = conditionText.split(":");
            String requiredMajor = (parts.length > 1) ? parts[1].trim() : "";
            if (userMajor != null && userMajor.equalsIgnoreCase(requiredMajor)) {
                return new ScoringResult(1, null, null);
            } else {
                String reason = "Ngành học của bạn (" + userMajor + ") chưa phù hợp (" + requiredMajor + ")";
                return new ScoringResult(0, reason, "Cập nhật ngành thành " + requiredMajor);
            }
        }
        return new ScoringResult(-1, null, null);
    }
}
