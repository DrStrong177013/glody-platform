package com.glody.glody_platform.community.repository;

import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.community.entity.Reaction;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByPost(Post post);
    Optional<Reaction> findByPostAndUser(Post post, User user);
}
