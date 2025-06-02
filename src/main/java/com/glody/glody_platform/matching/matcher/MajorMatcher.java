package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class MajorMatcher implements MatchCriterion {
    @Override
    public MatchResult evaluate(UserProfile profile, Program program) {
        if (program.getMajors() != null && profile.getMajor() != null) {
            boolean match = program.getMajors().stream()
                    .anyMatch(m -> m.toLowerCase().contains(profile.getMajor().toLowerCase()));
            if (match) {
                return MatchResult.passed("Ngành học phù hợp.");
            } else {
                return MatchResult.failed("Ngành học không khớp.");
            }
        }
        return MatchResult.failed("Thiếu thông tin ngành học.");
    }
}
