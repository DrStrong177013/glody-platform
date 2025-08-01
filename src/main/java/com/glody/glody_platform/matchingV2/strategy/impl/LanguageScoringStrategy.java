package com.glody.glody_platform.matchingV2.strategy.impl;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.university.repository.ProgramRequirementRepository;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LanguageScoringStrategy implements ScoringStrategy {
    private final ProgramRequirementRepository requirementRepo;

    public LanguageScoringStrategy(ProgramRequirementRepository requirementRepo) {
        this.requirementRepo = requirementRepo;
    }

    @Override
    public String getName() {
        return "LANGUAGE";
    }

    @Override
    public double score(UserProfile profile, Program program) {
        // 1) Lấy ProgramRequirement từ DB
        Optional<ProgramRequirement> optReq = requirementRepo.findByProgramId(program.getId());
        if (optReq.isEmpty()) {
            // Nếu không có yêu cầu, coi là thỏa
            return 1.0;
        }
        String fullReq = optReq.get().getLanguageRequirement();
        if (fullReq == null || fullReq.isBlank()) {
            return 1.0;
        }

        // 2) Tách exam name và điểm yêu cầu (ví dụ "IELTS 6.5")
        String[] parts = fullReq.split("\\s+", 2);
        String examName = parts[0];
        final Double requiredScore;
        if (parts.length == 2) {
            Double tmp;
            try {
                tmp = Double.parseDouble(parts[1]);
            } catch (NumberFormatException e) {
                tmp = null;
            }
            requiredScore = tmp;
        } else {
            requiredScore = null;
        }

        // 3) Duyệt danh sách chứng chỉ user
        List<LanguageCertificate> certs = profile.getLanguageCertificates();
        if (certs == null || certs.isEmpty()) {
            return 0.0;
        }
        for (LanguageCertificate cert : certs) {
            if (cert.getCertificateName().equalsIgnoreCase(examName)
                    && (requiredScore == null || cert.getScore() >= requiredScore)) {
                return 1.0;
            }
        }
        return 0.0;
    }
}
