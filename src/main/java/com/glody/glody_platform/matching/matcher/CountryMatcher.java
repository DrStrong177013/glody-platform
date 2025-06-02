package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class CountryMatcher implements MatchCriterion {
    @Override
    public MatchResult evaluate(UserProfile profile, Program program) {
        if (profile.getTargetCountry() != null &&
                program.getSchool() != null &&
                program.getSchool().getLocation() != null) {

            if (profile.getTargetCountry().equalsIgnoreCase(program.getSchool().getLocation())) {
                return MatchResult.passed("Quốc gia phù hợp.");
            } else {
                return MatchResult.failed("Khác quốc gia mục tiêu.");
            }
        }
        return MatchResult.failed("Thiếu thông tin quốc gia.");
    }
}
