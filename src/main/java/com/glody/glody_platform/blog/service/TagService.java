package com.glody.glody_platform.blog.service;

import com.glody.glody_platform.blog.dto.TagRequestDto;
import com.glody.glody_platform.blog.dto.TagResponseDto;
import com.glody.glody_platform.blog.entity.PostTag;
import com.glody.glody_platform.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private boolean existsByName(String name) {
        return tagRepository.existsByNameIgnoreCaseAndIsDeletedFalse(name);
    }

    public List<PostTag> getAll() {
        return tagRepository.findAll().stream()
                .filter(tag -> !Boolean.TRUE.equals(tag.getIsDeleted()))
                .toList();
    }

    public TagResponseDto create(TagRequestDto dto) {
        if (existsByName(dto.getName())) {
            throw new RuntimeException("Tag đã tồn tại với tên: " + dto.getName());
        }

        PostTag tag = new PostTag();
        tag.setName(dto.getName());
        PostTag saved = tagRepository.save(tag);

        return toDto(saved);
    }

    public TagResponseDto update(Long id, TagRequestDto dto) {
        PostTag tag = tagRepository.findById(id)
                .filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("Tag không tồn tại"));

        boolean duplicate = tagRepository.existsByNameIgnoreCaseAndIsDeletedFalse(dto.getName()) &&
                !tag.getName().equalsIgnoreCase(dto.getName());

        if (duplicate) {
            throw new RuntimeException("Tên tag đã tồn tại: " + dto.getName());
        }

        tag.setName(dto.getName());
        PostTag updated = tagRepository.save(tag);
        return toDto(updated);
    }

    public void delete(Long id) {
        PostTag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag không tồn tại"));
        tag.setIsDeleted(true);
        tag.setDeletedAt(LocalDateTime.now());
        tagRepository.save(tag);
    }

    private TagResponseDto toDto(PostTag tag) {
        TagResponseDto dto = new TagResponseDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setCreatedAt(tag.getCreatedAt());
        dto.setUpdatedAt(tag.getUpdatedAt());
        return dto;
    }
}
