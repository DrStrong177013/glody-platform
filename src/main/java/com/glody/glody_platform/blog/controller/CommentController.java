package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.CommentRequestDto;
import com.glody.glody_platform.blog.dto.CommentResponseDto;
import com.glody.glody_platform.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller xử lý các chức năng liên quan đến bình luận bài viết.
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "Quản lý bình luận cho bài viết")
public class CommentController {

    private final CommentService commentService;

    /**
     * Thêm bình luận vào bài viết.
     * Nếu muốn trả lời 1 bình luận khác thì truyền parentId trong DTO.
     *
     * @param dto Dữ liệu bình luận
     * @return Phản hồi thành công
     */
    @Operation(summary = "Thêm bình luận vào bài viết (truyền parentId nếu là trả lời một bình luận)")
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentRequestDto dto) {
        commentService.addComment(dto);
        return ResponseEntity.ok("✅ Bình luận đã được thêm!");
    }

    /**
     * Lấy danh sách bình luận của một bài viết theo ID.
     *
     * @param postId ID bài viết
     * @param page   Số trang (mặc định 0)
     * @param size   Kích thước trang (mặc định 10)
     * @return Danh sách bình luận dạng phân trang
     */
    @Operation(summary = "Lấy danh sách bình luận theo bài viết")
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentResponseDto>> getByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentResponseDto> comments = commentService.getCommentsByPost(postId, PageRequest.of(page, size));
        return ResponseEntity.ok(comments);
    }

    /**
     * Xoá mềm một bình luận (đánh dấu đã xoá thay vì xoá hẳn).
     *
     * @param commentId ID bình luận
     * @return Phản hồi xoá thành công
     */
    @Operation(summary = "Xoá mềm bình luận (không xoá khỏi DB)")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.softDelete(commentId);
        return ResponseEntity.ok("🗑️ Đã xoá bình luận.");
    }
}
