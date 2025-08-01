package com.glody.glody_platform.matchingV2.strategy;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;

public interface ScoringStrategy {
    /**
     * Định danh để map tới weight tương ứng.
     */
    String getName();

    /**
     * Tính điểm 0–1 cho 1 cặp (userProfile, program).
     */
    double score(UserProfile profile, Program program);
}