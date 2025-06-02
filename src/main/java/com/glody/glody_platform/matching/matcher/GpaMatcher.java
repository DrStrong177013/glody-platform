package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.matching.util.GpaNormalizer;
import com.glody.glody_platform.matching.util.RequirementParser;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class GpaMatcher implements MatchCriterion {

    @Override
    public MatchResult evaluate(UserProfile profile, Program program) {
        if (profile.getGpa() == null || program.getRequirement() == null) {
            return MatchResult.failed("Thiếu thông tin GPA.");
        }

        try {
            // GPA yêu cầu của chương trình, luôn ở thang 10
            double requiredGpa = RequirementParser.extractGpa(program.getRequirement().getGpaRequirement());

            // GPA người dùng đã chuẩn hóa về thang 10
            double userGpa10 = GpaNormalizer.normalize(profile);

            boolean passed = userGpa10 >= requiredGpa;

            return passed
                    ? MatchResult.passed("GPA đạt yêu cầu (%.2f ≥ %.2f)".formatted(userGpa10, requiredGpa))
                    : MatchResult.failed("GPA không đủ yêu cầu (%.2f < %.2f)".formatted(userGpa10, requiredGpa));

        } catch (Exception e) {
            return MatchResult.failed("Không thể phân tích GPA yêu cầu.");
        }
    }
}
