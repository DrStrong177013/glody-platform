package com.glody.glody_platform.matching.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingScoreDto {
    private String criteria;
    private Double score;
    private String reason;
}