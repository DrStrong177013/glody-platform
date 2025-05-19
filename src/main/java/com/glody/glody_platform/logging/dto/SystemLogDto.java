package com.glody.glody_platform.logging.dto;

import lombok.Data;

@Data
public class SystemLogDto {
    private Long userId;
    private String action;
    private String metadata;
}
