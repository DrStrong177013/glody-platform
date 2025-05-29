package com.glody.glody_platform.blog.service;

import com.glody.glody_platform.blog.dto.CategoryRequestDto;
import com.glody.glody_platform.blog.dto.CategoryResponseDto;
import com.glody.glody_platform.blog.entity.Category;
import com.glody.glody_platform.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCaseAndIsDeletedFalse(name);
    }
    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }


    public List<Category> getAll() {
        return categoryRepository.findAll().stream()
                .filter(cat -> !Boolean.TRUE.equals(cat.getIsDeleted()))
                .toList();
    }


    public CategoryResponseDto create(CategoryRequestDto dto) {
        if (existsByName(dto.getName())) {
            throw new RuntimeException("Category đã tồn tại với tên: " + dto.getName());
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(generateSlug(dto.getName()));

        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        boolean duplicate = categoryRepository.existsByNameIgnoreCaseAndIsDeletedFalse(dto.getName()) &&
                !category.getName().equalsIgnoreCase(dto.getName());

        if (duplicate) {
            throw new RuntimeException("Tên category đã tồn tại: " + dto.getName());
        }

        category.setName(dto.getName());
        category.setSlug(generateSlug(dto.getName()));
        return toDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
        category.setIsDeleted(true);
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    private CategoryResponseDto toDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
