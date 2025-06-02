package com.glody.glody_platform.matching.matcher.scholarship;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.users.entity.UserProfile;

public interface ScholarshipMatchCriterion {
    int score(UserProfile profile, Scholarship scholarship, Program program);
    String getReason(); // Optional: lý do để show ra UI nếu muốn
}
