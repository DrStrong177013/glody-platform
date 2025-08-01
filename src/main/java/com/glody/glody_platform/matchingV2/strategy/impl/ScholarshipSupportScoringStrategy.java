package com.glody.glody_platform.matchingV2.strategy.impl;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class ScholarshipSupportScoringStrategy implements ScoringStrategy {
    @Override
    public String getName() {
        return "SCHOLARSHIP_SUPPORT";
    }

    @Override
    public double score(UserProfile profile, Program program) {
        // cột scholarship_support trong DB được map thành Boolean scholarshipSupport
        Boolean support = program.getScholarshipSupport();
        return Boolean.TRUE.equals(support) ? 1.0 : 0.0;
    }
}
