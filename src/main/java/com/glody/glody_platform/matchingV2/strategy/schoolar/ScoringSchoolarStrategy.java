package com.glody.glody_platform.matchingV2.strategy.schoolar;

import com.glody.glody_platform.matchingV2.dto.ScoringResult;
import com.glody.glody_platform.users.dto.UserProfileDto;

import java.util.Map;

public interface ScoringSchoolarStrategy  {
    ScoringResult score(UserProfileDto userProfile, String conditionText, Map<String, Object> customCriteria);
}