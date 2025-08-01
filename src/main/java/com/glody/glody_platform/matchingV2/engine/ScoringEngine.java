package com.glody.glody_platform.matchingV2.engine;

import com.glody.glody_platform.matchingV2.strategy.ScoringStrategy;
import com.glody.glody_platform.matchingV2.config.ScoringConfigService;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringEngine {
    private final List<ScoringStrategy> strategies;
    private final ScoringConfigService configService;

    public ScoringEngine(List<ScoringStrategy> strategies,
                         ScoringConfigService configService) {
        this.strategies = strategies;
        this.configService = configService;
    }

    /** Trả về điểm 0–100% */
    public double calculateScore(UserProfile profile, Program program) {
        double totalWeight = strategies.stream()
                .mapToDouble(s -> configService.getWeight(s.getName()))
                .sum();
        double weighted = strategies.stream()
                .mapToDouble(s -> s.score(profile, program) * configService.getWeight(s.getName()))
                .sum();

        return totalWeight > 0
                ? (weighted / totalWeight) * 100
                : 0.0;
    }

    /** Cho phép lấy toàn bộ chiến lược đang đăng ký */
    public List<ScoringStrategy> getStrategies() {
        return strategies;
    }
}
