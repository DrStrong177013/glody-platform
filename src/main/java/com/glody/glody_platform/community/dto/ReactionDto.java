package com.glody.glody_platform.community.dto;

import lombok.Data;

@Data
public class ReactionDto {
    private Long postId;
    private Long userId;
    private String type; // LIKE, LOVE, CLAP, etc.
}
