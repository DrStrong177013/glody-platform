package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class ScholarshipMatcher implements MatchCriterion {
    @Override
    public MatchResult evaluate(UserProfile profile, Program program) {
        if (program.getScholarshipSupport() != null && program.getScholarshipSupport()) {
            return MatchResult.passed("Có hỗ trợ học bổng.");
        }
        return MatchResult.failed("Không hỗ trợ học bổng.");
    }
}
