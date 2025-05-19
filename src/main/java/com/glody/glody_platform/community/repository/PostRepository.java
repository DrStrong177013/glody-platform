package com.glody.glody_platform.community.repository;

import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserAndIsDeletedFalse(User user);
    List<Post> findByPostTypeAndIsDeletedFalse(String postType);
}
