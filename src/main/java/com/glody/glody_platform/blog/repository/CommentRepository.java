package com.glody.glody_platform.blog.repository;

import com.glody.glody_platform.blog.entity.Comment;
import com.glody.glody_platform.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostAndIsDeletedFalse(Post post, Pageable pageable);
}
