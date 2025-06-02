package com.glody.glody_platform.matching.matcher.scholarship;

import com.glody.glody_platform.matching.matcher.scholarship.language.LanguageCertificateHandler;
import com.glody.glody_platform.matching.util.RequirementParser;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.service.LanguageCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LanguageScoreMatcher implements ScholarshipMatchCriterion {

    private final List<LanguageCertificateHandler> handlers;
    private final LanguageCertificateService certificateService;

    private String reason = "";

    @Override
    public int score(UserProfile profile, Scholarship scholarship, Program program) {
        List<LanguageCertificateResponse> certs = certificateService.getCertificates(profile.getUser().getId());
        if (certs == null || certs.isEmpty() || scholarship.getConditions() == null) return 0;

        int total = 0;

        for (String condition : scholarship.getConditions()) {
            String type = RequirementParser.extractRequiredLanguageType(condition);
            double required = RequirementParser.extractRequiredLanguageScore(condition);

            LanguageCertificateHandler handler = handlers.stream()
                    .filter(h -> h.supports(type))
                    .findFirst()
                    .orElse(null);

            if (handler == null) {
                reason = "Không hỗ trợ loại chứng chỉ: " + type;
                continue;
            }

            double score = handler.getScore(certs);

            if (score >= required) {
                total += 20;
                reason = type + " đạt yêu cầu (" + score + " ≥ " + required + ")";
            } else {
                reason = type + " không đủ (" + score + " < " + required + ")";
            }
        }

        return total;
    }

    @Override
    public String getReason() {
        return reason;
    }
}
