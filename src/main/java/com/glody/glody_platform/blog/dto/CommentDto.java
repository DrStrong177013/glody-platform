package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDto {

    @Schema(description = "ID bài viết", example = "5")
    private Long postId;

    @Schema(description = "ID người dùng (nếu đã đăng nhập)", example = "10")
    private Long userId;

    @Schema(description = "Tên người gửi bình luận (nếu không đăng nhập)", example = "Nguyễn Văn A")
    private String name;

    @Schema(description = "Email người gửi", example = "nguyenvana@example.com")
    private String email;

    @Schema(description = "Nội dung bình luận", example = "Cảm ơn bạn đã chia sẻ thông tin hữu ích.")
    private String content;
}
