package com.glody.glody_platform.matching.matcher.scholarship;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class MajorScoreMatcher implements ScholarshipMatchCriterion {

    private String reason = "";

    @Override
    public int score(UserProfile profile, Scholarship scholarship, Program program) {
        if (profile.getMajor() == null || program == null || program.getMajors() == null) {
            reason = "Thiếu thông tin ngành học hoặc chương trình";
            return 0;
        }

        String major = profile.getMajor().toLowerCase();
        boolean matched = program.getMajors().stream()
                .anyMatch(m -> m.toLowerCase().contains(major));

        if (matched) {
            reason = "Ngành học phù hợp với chương trình liên kết học bổng";
            return 20;
        } else {
            reason = "Ngành học không khớp với chương trình";
            return 0;
        }
    }

    @Override
    public String getReason() {
        return reason;
    }
}
