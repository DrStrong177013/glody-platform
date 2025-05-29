package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    @Schema(description = "Tiêu đề bài viết", example = "Làm hồ sơ du học Hàn Quốc")
    private String title;

    @Schema(description = "Slug duy nhất dùng trong URL", example = "lam-ho-so-du-hoc-han-quoc")
    private String slug;

    @Schema(description = "Link ảnh thumbnail", example = "https://example.com/images/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "Tóm tắt nội dung bài viết", example = "Hướng dẫn từng bước chuẩn bị hồ sơ du học...")
    private String excerpt;

    @Schema(description = "Nội dung HTML bài viết", example = "<p>Chi tiết từng bước...</p>")
    private String content;

    @Schema(description = "Trạng thái đã publish hay chưa", example = "true")
    private Boolean published;

    @Schema(description = "Thời điểm bài viết được xuất bản", example = "2025-05-30T10:00:00")
    private LocalDateTime publishDate;

    @Schema(description = "Số lượt xem bài viết", example = "124")
    private Integer viewCount;

    @Schema(description = "ID quốc gia liên quan", example = "1")
    private Long countryId;

    @Schema(description = "ID chuyên mục", example = "2")
    private Long categoryId;

    @Schema(description = "Danh sách ID của các thẻ (tags)", example = "[1, 2, 5]")
    private List<Long> tagIds;
}
