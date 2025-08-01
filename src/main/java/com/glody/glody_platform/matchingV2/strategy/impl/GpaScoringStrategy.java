package com.glody.glody_platform.matchingV2.strategy.impl;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class GpaScoringStrategy implements ScoringStrategy {
    @Override public String getName() { return "GPA"; }

    @Override
    public double score(UserProfile profile, Program program) {
        // 1) Lấy GPA và scale của user
        double userGpa   = profile.getGpa();       // e.g. 8.0
        double userScale = profile.getGpaScale();  // e.g. 10.0

        // 2) Tách value và scale của program (hiện đang lưu string "x/y")
        String[] parts = program.getRequirement().getGpaRequirement().split("/");
        double reqValue = Double.parseDouble(parts[0]);  // e.g. 3.2
        double reqScale = Double.parseDouble(parts[1]);  // e.g. 4.0

        // 3) Chuẩn hóa user GPA về cùng thang với chương trình
        double normalizedUserGpa = (userGpa / userScale) * reqScale;

        // 4) Tính điểm
        if (normalizedUserGpa >= reqValue) {
            return 1.0;
        }
        return normalizedUserGpa / reqValue;
    }
}

