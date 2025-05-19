package com.glody.glody_platform.community.repository;

import com.glody.glody_platform.community.entity.Comment;
import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostAndIsDeletedFalse(Post post);
    List<Comment> findByUserAndIsDeletedFalse(User user);
}
