package com.glody.glody_platform.matchingV2.strategy.impl;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class CountryScoringStrategy implements ScoringStrategy {
    @Override
    public String getName() {
        return "COUNTRY";
    }

    @Override
    public double score(UserProfile profile, Program program) {
        // Lấy mã/quốc gia mục tiêu (ví dụ "Anh", "Canada", "Úc") :contentReference[oaicite:4]{index=4}
        String targetCountry = profile.getTargetCountry();
        // Lấy chuỗi location của School (ví dụ "Cambridge, Anh", "Toronto, Canada") :contentReference[oaicite:5]{index=5}
        String schoolLocation = program.getSchool().getLocation();

        if (targetCountry == null || schoolLocation == null) {
            return 0.0;
        }
        // So khớp nếu location chứa đúng targetCountry
        return schoolLocation.toLowerCase()
                .contains(targetCountry.toLowerCase())
                ? 1.0
                : 0.0;
    }
}
