package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.matching.util.RequirementParser;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class LanguageMatcher implements MatchCriterion {
    @Override
    public MatchResult evaluate(UserProfile profile, Program program) {
        if (program.getRequirement() == null ||
                program.getRequirement().getLanguageRequirement() == null ||
                profile.getLanguageCertificates() == null) {
            return MatchResult.failed("Thiếu thông tin chứng chỉ ngôn ngữ.");
        }

        String requiredCert = RequirementParser.extractRequiredLanguageType(program.getRequirement().getLanguageRequirement());
        double requiredScore = RequirementParser.extractRequiredLanguageScore(program.getRequirement().getLanguageRequirement());

        boolean passed = profile.getLanguageCertificates().stream()
                .filter(cert -> cert.getCertificateName().equalsIgnoreCase(requiredCert))
                .anyMatch(cert -> cert.getScore() >= requiredScore);

        return passed ?
                MatchResult.passed("Chứng chỉ ngôn ngữ đạt yêu cầu.") :
                MatchResult.failed("Không đạt điểm chứng chỉ ngôn ngữ.");
    }
}
