package com.glody.glody_platform.logging.dto;

import lombok.Data;

@Data
public class AIModelLogDto {
    private Long userId;
    private String modelName;
    private String input;
    private String output;
}
