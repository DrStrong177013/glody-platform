package com.glody.glody_platform.blog.repository;

import com.glody.glody_platform.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    boolean existsBySlug(String slug);
    Post findBySlug(String slug);
    Page<Post> findAllByIsDeletedTrue(Pageable pageable);

}
