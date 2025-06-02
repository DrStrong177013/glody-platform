package com.glody.glody_platform.matching.matcher.scholarship.language;

import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.entity.LanguageCertificate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IELTSHandler implements LanguageCertificateHandler {

    @Override
    public boolean supports(String type) {
        return "IELTS".equalsIgnoreCase(type);
    }

    @Override
    public double getScore(List<LanguageCertificateResponse> certificates) {
        return certificates.stream()
                .filter(c -> "IELTS".equalsIgnoreCase(c.getCertificateName()))
                .mapToDouble(LanguageCertificateResponse::getScore)
                .average()
                .orElse(0.0);
    }
}

