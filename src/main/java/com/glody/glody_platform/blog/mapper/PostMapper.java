package com.glody.glody_platform.blog.mapper;

import com.glody.glody_platform.blog.dto.PostResponseDto;
import com.glody.glody_platform.blog.entity.Post;
import com.glody.glody_platform.blog.entity.PostTag;

public class PostMapper {
    public PostResponseDto toDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setThumbnailUrl(post.getThumbnailUrl());
        dto.setExcerpt(post.getExcerpt());
        dto.setContent(post.getContent());
        dto.setPublished(post.getPublished());
        dto.setPublishDate(post.getPublishDate());
        dto.setViewCount(post.getViewCount());

        dto.setCountryName(post.getCountry() != null ? post.getCountry().getName() : null);
        dto.setCategoryName(post.getCategory() != null ? post.getCategory().getName() : null);

        dto.setTagNames(post.getTags().stream()
                .map(PostTag::getName)
                .toList());

        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        return dto;
    }

}
