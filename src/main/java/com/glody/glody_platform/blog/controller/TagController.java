package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.TagRequestDto;
import com.glody.glody_platform.blog.dto.TagResponseDto;
import com.glody.glody_platform.blog.entity.PostTag;
import com.glody.glody_platform.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Controller", description = "Quản lý thẻ gắn bài viết")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Lấy danh sách thẻ chưa bị xoá")
    @GetMapping
    public List<PostTag> getAll() {
        return tagService.getAll();
    }

    @Operation(summary = "Tạo thẻ mới (Admin)")
    @PostMapping
    public TagResponseDto create(@RequestBody TagRequestDto dto) {
        return tagService.create(dto);
    }

    @Operation(summary = "Cập nhật thông tin thẻ (Admin)")
    @PutMapping("/{id}")
    public TagResponseDto update(@PathVariable Long id, @RequestBody TagRequestDto dto) {
        return tagService.update(id, dto);
    }

    @Operation(summary = "Xoá mềm thẻ (Admin)")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        tagService.delete(id);
        return "Tag deleted (soft)";
    }
}

