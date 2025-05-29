package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private Long id;

    @Schema(description = "Tiêu đề bài viết", example = "Hướng dẫn du học Canada 2025")
    private String title;

    @Schema(description = "Slug bài viết", example = "huong-dan-du-hoc-canada-2025")
    private String slug;

    @Schema(description = "Ảnh thumbnail", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "Mô tả ngắn", example = "Tổng hợp những điều cần chuẩn bị trước khi du học...")
    private String excerpt;

    @Schema(description = "Nội dung bài viết", example = "Bài viết này sẽ giúp bạn chuẩn bị hồ sơ...")
    private String content;

    @Schema(description = "Trạng thái đã xuất bản hay chưa", example = "true")
    private Boolean published;

    @Schema(description = "Thời điểm xuất bản", example = "2025-06-01T08:00:00")
    private LocalDateTime publishDate;

    @Schema(description = "Số lượt xem", example = "1024")
    private Integer viewCount;

    @Schema(description = "Tên quốc gia", example = "Canada")
    private String countryName;

    @Schema(description = "Tên danh mục", example = "Du học")
    private String categoryName;

    @Schema(description = "Danh sách thẻ tag", example = "[\"visa\", \"học bổng\"]")
    private List<String> tagNames;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
