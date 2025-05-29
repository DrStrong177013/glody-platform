package com.glody.glody_platform.blog.service;

import com.glody.glody_platform.blog.dto.PostRequestDto;
import com.glody.glody_platform.blog.dto.PostResponseDto;
import com.glody.glody_platform.blog.entity.Category;
import com.glody.glody_platform.blog.entity.Post;
import com.glody.glody_platform.blog.entity.PostTag;
import com.glody.glody_platform.blog.repository.CategoryRepository;
import com.glody.glody_platform.blog.repository.PostRepository;
import com.glody.glody_platform.blog.repository.TagRepository;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.repository.CountryRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;

    public Page<PostResponseDto> getAllPosts(String keyword,
                                             Boolean published,
                                             Long categoryId,
                                             Long countryId,
                                             List<Long> tagIds,
                                             Pageable pageable) {

        Specification<Post> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                Predicate titleLike = cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
                Predicate contentLike = cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%");
                predicates.add(cb.or(titleLike, contentLike));
            }

            if (published != null) {
                predicates.add(cb.equal(root.get("published"), published));
            }

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (countryId != null) {
                predicates.add(cb.equal(root.get("country").get("id"), countryId));
            }

            if (tagIds != null && !tagIds.isEmpty()) {
                Join<Post, PostTag> tagJoin = root.join("tags");
                predicates.add(tagJoin.get("id").in(tagIds));
                query.distinct(true);
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Post> postPage = postRepository.findAll(spec, pageable);

        return postPage.map(this::toDto);
    }
    @Transactional
    public PostResponseDto createOrUpdatePost(Long postId, PostRequestDto dto) {
        Post post = (postId != null)
                ? postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"))
                : new Post();

        post.setTitle(dto.getTitle());
        post.setSlug(dto.getSlug());
        post.setThumbnailUrl(dto.getThumbnailUrl());
        post.setExcerpt(dto.getExcerpt());
        post.setContent(dto.getContent());
        post.setPublished(dto.getPublished() != null ? dto.getPublished() : false);
        post.setPublishDate(dto.getPublishDate());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            post.setCategory(category);
        }

        if (dto.getCountryId() != null) {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            post.setCountry(country);
        }

        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            Set<PostTag> tags = new HashSet<>(tagRepository.findAllById(dto.getTagIds()));
            post.setTags(tags);
        }

        Post saved = postRepository.save(post);
        return toDto(saved);
    }
    @Transactional
    public void softDelete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setIsDeleted(true);
        post.setDeletedAt(java.time.LocalDateTime.now());
        postRepository.save(post);
    }

    @Transactional
    public void restore(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setIsDeleted(false);
        post.setDeletedAt(null);
        postRepository.save(post);
    }

    public Page<PostResponseDto> getDeletedPosts(Pageable pageable) {
        Page<Post> deletedPosts = postRepository.findAllByIsDeletedTrue(pageable);
        return deletedPosts.map(this::toDto);
    }
    @Transactional
    public void increaseViewCount(String slug) {
        Post post = postRepository.findBySlug(slug);
        if (post == null || Boolean.TRUE.equals(post.getIsDeleted())) {
            throw new RuntimeException("Post not found or deleted");
        }

        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post); // đảm bảo view tăng được lưu lại
    }


    private PostResponseDto toDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setThumbnailUrl(post.getThumbnailUrl());
        dto.setExcerpt(post.getExcerpt());
        dto.setContent(post.getContent());
        dto.setPublished(post.getPublished());
        dto.setPublishDate(post.getPublishDate());
        dto.setViewCount(post.getViewCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        dto.setCategoryName(post.getCategory() != null ? post.getCategory().getName() : null);
        dto.setCountryName(post.getCountry() != null ? post.getCountry().getName() : null);
        dto.setTagNames(post.getTags().stream().map(PostTag::getName).toList());

        return dto;
    }
}
