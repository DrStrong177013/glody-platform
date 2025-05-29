package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequestDto {

    @Schema(description = "Tiêu đề bài viết", example = "Top 5 học bổng du học Mỹ")
    private String title;

    @Schema(description = "Slug bài viết (URL)", example = "top-5-hoc-bong-du-hoc-my")
    private String slug;

    @Schema(description = "Thumbnail URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "Mô tả ngắn gọn", example = "Cùng tìm hiểu 5 học bổng giá trị nhất cho sinh viên quốc tế")
    private String excerpt;

    @Schema(description = "Nội dung chính")
    private String content;

    @Schema(description = "Trạng thái public", example = "true")
    private Boolean published;

    @Schema(description = "Ngày public (nếu có)")
    private LocalDateTime publishDate;

    @Schema(description = "ID chuyên mục", example = "1")
    private Long categoryId;

    @Schema(description = "ID quốc gia", example = "2")
    private Long countryId;

    @Schema(description = "Danh sách ID tag", example = "[1,2,3]")
    private List<Long> tagIds;
}
