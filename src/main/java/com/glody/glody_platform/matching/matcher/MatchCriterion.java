package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;

public interface MatchCriterion {
    MatchResult evaluate(UserProfile profile, Program program);
}
