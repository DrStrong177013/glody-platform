package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.CommentRequestDto;
import com.glody.glody_platform.blog.dto.CommentResponseDto;
import com.glody.glody_platform.blog.service.CommentService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.io.Console;
import java.util.stream.Collectors;

/**
 * REST Controller xử lý các chức năng liên quan đến bình luận bài viết.
 */
@Slf4j
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
    @Operation(summary = "Thêm bình luận vào bài viết, truyền parentId nếu là trả lời một bình luận (Auth)")
    @PostMapping
    public ResponseEntity<String> addComment(@AuthenticationPrincipal User currentUser, @RequestBody CommentRequestDto dto) {
        long currentUserId = currentUser.getId();
        commentService.addComment(currentUserId,dto);
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
    @Operation(summary = "Xoá mềm bình luận (Auth). Nếu là Admin thì có quyền xóa bình luận của người khác")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long commentId
    ) {
        long userId = currentUser.getId();

        // Lấy Authentication từ SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Lấy danh sách authority strings (e.g. "ROLE_USER", "ROLE_ADMIN")
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Log thông tin user và authorities
        log.info("User ID={} attempts to delete comment {} with authorities=[{}]",
                userId, commentId, authorities);

        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        if (isAdmin) {
            commentService.softDeleteByAdmin(commentId);
            log.debug("Admin user ID={} soft-deleted comment {}", userId, commentId);
        } else {
            commentService.softDelete(userId, commentId);
            log.debug("User ID={} soft-deleted own comment {}", userId, commentId);
        }

        return ResponseEntity.ok("🗑️ Đã xoá bình luận.");
    }
}
