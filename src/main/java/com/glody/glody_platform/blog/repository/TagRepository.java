package com.glody.glody_platform.blog.repository;

import com.glody.glody_platform.blog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByNameContainingIgnoreCase(String keyword);
    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);

}
