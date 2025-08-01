package com.glody.glody_platform.matchingV2.config;

import com.glody.glody_platform.matchingV2.entity.ScoringWeight;
import com.glody.glody_platform.matchingV2.repository.ScoringWeightRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoringConfigService {
    private final ScoringWeightRepository repo;
    private Map<String, Double> weightCache;

    public ScoringConfigService(ScoringWeightRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        weightCache = repo.findAll().stream()
                .collect(Collectors.toMap(ScoringWeight::getName, ScoringWeight::getWeight));
    }

    public double getWeight(String name) {
        return weightCache.getOrDefault(name, 0.0);
    }
}
