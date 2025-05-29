package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.PostRequestDto;
import com.glody.glody_platform.blog.dto.PostResponseDto;
import com.glody.glody_platform.blog.service.PostService;
import com.glody.glody_platform.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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

        PageResponse<PostResponseDto> response = new PageResponse<>(postPage.getContent(), pageInfo);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Admin tạo bài viết mới")
    @PostMapping("/admin")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.createOrUpdatePost(null, dto));
    }

    @Operation(summary = "Admin cập nhật bài viết")
    @PutMapping("/admin/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.createOrUpdatePost(id, dto));
    }
    @Operation(summary = "Xoá mềm bài viết")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        postService.softDelete(id);
        return ResponseEntity.ok("Bài viết đã được xoá mềm");
    }

    @Operation(summary = "Khôi phục bài viết đã xoá")
    @PutMapping("/admin/{id}/restore")
    public ResponseEntity<String> restore(@PathVariable Long id) {
        postService.restore(id);
        return ResponseEntity.ok("Bài viết đã được khôi phục");
    }
    @Operation(summary = "Lấy danh sách bài viết đã bị xoá mềm (chỉ admin)")
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
    @Operation(summary = "Tăng lượt xem cho bài viết (qua slug)")
    @PatchMapping("/slug/{slug}/view")
    public ResponseEntity<String> increaseView(@PathVariable String slug) {
        postService.increaseViewCount(slug);
        return ResponseEntity.ok("Đã tăng view cho bài viết");
    }


}
