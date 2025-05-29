package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.CommentRequestDto;
import com.glody.glody_platform.blog.dto.CommentResponseDto;
import com.glody.glody_platform.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Thêm bình luận vào bài viết  (parentId để comment 1 comment khác trong bài viết, nếu chỉ là comment bài viết thì xóa parentId)")
    @PostMapping
    public String addComment(@RequestBody CommentRequestDto dto) {
        commentService.addComment(dto);
        return "Bình luận đã được thêm!";
    }

    @Operation(summary = "Lấy danh sách bình luận theo bài viết")
    @GetMapping("/post/{postId}")
    public Page<CommentResponseDto> getByPost(@PathVariable Long postId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return commentService.getCommentsByPost(postId, PageRequest.of(page, size));
    }

    @Operation(summary = "Xoá mềm bình luận")
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.softDelete(commentId);
        return "Đã xoá bình luận.";
    }
}
