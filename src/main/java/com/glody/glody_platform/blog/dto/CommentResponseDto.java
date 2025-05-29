package com.glody.glody_platform.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userName;
    private LocalDateTime createdAt;
    private Long parentId;
}
