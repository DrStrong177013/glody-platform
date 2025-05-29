package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentRequestDto {
    @Schema(description = "ID bài viết", example = "12")
    private Long postId;

    @Schema(description = "ID người dùng", example = "1")
    private Long userId;

    @Schema(description = "Nội dung bình luận", example = "Bài viết rất hay!")
    private String content;

    @Schema(description = "ID bình luận cha (nếu là phản hồi)", example = "null")
    private Long parentId;
}
