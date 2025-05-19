package com.glody.glody_platform.logging.dto;

import lombok.Data;

@Data
public class FeedbackDto {
    private Long userId;
    private String content;
}
