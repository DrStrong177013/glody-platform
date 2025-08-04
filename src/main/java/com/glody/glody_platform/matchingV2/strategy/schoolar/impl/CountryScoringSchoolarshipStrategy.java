package com.glody.glody_platform.matchingV2.strategy.schoolar.impl;


import com.glody.glody_platform.matchingV2.dto.ScoringResult;
import com.glody.glody_platform.matchingV2.strategy.schoolar.ScoringSchoolarStrategy;
import com.glody.glody_platform.users.dto.UserProfileDto;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class CountryScoringSchoolarshipStrategy implements ScoringSchoolarStrategy {

    @Override
    public ScoringResult score(UserProfileDto userProfile, String conditionText, Map<String, Object> customCriteria) {
        // Condition về quốc gia thường không nằm trong điều kiện học bổng, mà lấy từ school
        String userCountry = customCriteria.get("targetCountry") != null
                ? (String) customCriteria.get("targetCountry")
                : userProfile.getTargetCountry();

        String schoolCountry = customCriteria.get("schoolCountry") != null
                ? (String) customCriteria.get("schoolCountry")
                : null; // phải truyền qua customCriteria khi gọi strategy này!

        if (schoolCountry != null && userCountry != null && userCountry.equalsIgnoreCase(schoolCountry)) {
            return new ScoringResult(1, null, null);
        } else {
            String reason = "Trường học bổng ở " + schoolCountry + " không khớp quốc gia mục tiêu (" + userCountry + ")";
            return new ScoringResult(0, reason, "Xem xét học bổng quốc gia khác hoặc cập nhật hồ sơ");
        }
    }
}
