package com.glody.glody_platform.matching.matcher.scholarship;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.matching.util.GpaNormalizer;
import org.springframework.stereotype.Component;

@Component
public class GpaScoreMatcher implements ScholarshipMatchCriterion {

    private String reason = "";

    @Override
    public int score(UserProfile profile, Scholarship scholarship, Program program) {
        double gpa10 = GpaNormalizer.normalize(profile);

        if (gpa10 >= 9.0) {
            reason = "GPA xuất sắc (≥9.0)";
            return 30;
        } else if (gpa10 >= 8.0) {
            reason = "GPA tốt (≥8.0)";
            return 25;
        } else if (gpa10 >= 7.0) {
            reason = "GPA khá (≥7.0)";
            return 20;
        } else if (gpa10 >= 6.0) {
            reason = "GPA trung bình (≥6.0)";
            return 10;
        } else {
            reason = "GPA thấp (<6.0)";
            return 0;
        }
    }


    @Override
    public String getReason() {
        return reason;
    }
}
