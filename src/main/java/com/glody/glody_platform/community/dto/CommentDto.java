package com.glody.glody_platform.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long postId;
    private Long userId;
    private String content;
}
