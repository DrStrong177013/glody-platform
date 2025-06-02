package com.glody.glody_platform.matching.matcher.scholarship.language;

import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
import java.util.List;

public interface LanguageCertificateHandler {
    boolean supports(String type);
    double getScore(List<LanguageCertificateResponse> certificates);
}
