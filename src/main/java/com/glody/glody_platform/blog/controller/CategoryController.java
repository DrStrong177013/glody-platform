package com.glody.glody_platform.blog.controller;

import com.glody.glody_platform.blog.dto.CategoryRequestDto;
import com.glody.glody_platform.blog.dto.CategoryResponseDto;
import com.glody.glody_platform.blog.entity.Category;
import com.glody.glody_platform.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "Quản lý chuyên mục bài viết")
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "Lấy danh sách category chưa bị xoá")
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @Operation(summary = "Tạo chuyên mục mới (Admin)")
    @PostMapping
    public CategoryResponseDto create(@RequestBody @Parameter(description = "Thông tin chuyên mục") CategoryRequestDto dto) {
        return categoryService.create(dto);
    }

    @Operation(summary = "Cập nhật thông tin chuyên mục (Admin)")
    @PutMapping("/{id}")
    public CategoryResponseDto update(@PathVariable Long id,
                                      @RequestBody @Parameter(description = "Thông tin cần cập nhật") CategoryRequestDto dto) {
        return categoryService.update(id, dto);
    }

    @Operation(summary = "Xoá mềm chuyên mục (Admin)")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "Category deleted (soft)";
    }
}
