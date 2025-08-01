package com.glody.glody_platform.matchingV2.strategy.impl;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MajorScoringStrategy implements ScoringStrategy {
    @Override
    public String getName() {
        return "MAJOR";
    }

    @Override
    public double score(UserProfile profile, Program program) {
        String userMajor = profile.getMajor();
        if (userMajor == null || program.getMajors() == null) {
            return 0.0;
        }

        List<String> majors = program.getMajors();  // giả sử đây là List<String>
        boolean match = majors.stream()
                .anyMatch(m -> m.equalsIgnoreCase(userMajor));

        return match ? 1.0 : 0.0;
    }
}
