package com.glody.glody_platform.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long userId;
    private String title;
    private String content;
    private String postType;
    private List<String> tags;
}
