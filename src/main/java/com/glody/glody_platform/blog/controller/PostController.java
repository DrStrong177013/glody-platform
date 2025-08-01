package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.PostRequestDto;
import com.glody.glody_platform.blog.dto.PostResponseDto;
import com.glody.glody_platform.blog.service.PostService;
import com.glody.glody_platform.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller cho quản lý bài viết (bao gồm CRUD, phân trang, tìm kiếm, và khôi phục).
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post Controller", description = "Quản lý bài viết blog")
public class PostController {

    private final PostService postService;

    /**
     * Lấy danh sách bài viết với phân trang, tìm kiếm, lọc theo nhiều tiêu chí.
     */
    @Operation(summary = "Lấy danh sách bài viết (phân trang, lọc, tìm kiếm)")
    @GetMapping
    public ResponseEntity<PageResponse<PostResponseDto>> getPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) List<Long> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PostResponseDto> postPage = postService.getAllPosts(keyword, published, categoryId, countryId, tagIds, pageable);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                postPage.hasNext(),
                postPage.hasPrevious()
        );

        return ResponseEntity.ok(new PageResponse<>(postPage.getContent(), pageInfo));
    }

    /**
     * Admin tạo mới bài viết.
     */
    @Operation(summary = "Tạo bài viết mới (Admin)")
    @PostMapping("/admin")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.createOrUpdatePost(null, dto));
    }

    /**
     * Admin cập nhật bài viết theo ID.
     */
    @Operation(summary = "Admin cập nhật bài viết (Admin)")
    @PutMapping("/admin/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.createOrUpdatePost(id, dto));
    }

    /**
     * Admin xoá mềm bài viết (không xoá khỏi DB).
     */
    @Operation(summary = "Xoá mềm bài viết (Admin)")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        postService.softDelete(id);
        return ResponseEntity.ok("🗑️ Bài viết đã được xoá mềm");
    }

    /**
     * Khôi phục bài viết đã bị xoá mềm.
     */
    @Operation(summary = "Khôi phục bài viết đã xoá (Admin)")
    @PutMapping("/admin/{id}/restore")
    public ResponseEntity<String> restore(@PathVariable Long id) {
        postService.restore(id);
        return ResponseEntity.ok("♻️ Bài viết đã được khôi phục");
    }

    /**
     * Lấy danh sách các bài viết đã bị xoá mềm.
     */
    @Operation(summary = "Lấy danh sách bài viết đã bị xoá mềm (Admin)")
    @GetMapping("/admin/deleted")
    public ResponseEntity<PageResponse<PostResponseDto>> getDeletedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deletedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PostResponseDto> postPage = postService.getDeletedPosts(pageable);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                postPage.hasNext(),
                postPage.hasPrevious()
        );

        return ResponseEntity.ok(new PageResponse<>(postPage.getContent(), pageInfo));
    }

    /**
     * Tăng lượt xem cho bài viết theo slug.
     */
    @Operation(summary = "Tăng lượt xem cho bài viết qua slug")
    @PatchMapping("/slug/{slug}/view")
    public ResponseEntity<String> increaseView(@PathVariable String slug) {
        postService.increaseViewCount(slug);
        return ResponseEntity.ok("👁️ Đã tăng lượt xem cho bài viết");
    }
}
