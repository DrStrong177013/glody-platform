package com.glody.glody_platform.community.repository;

import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.community.entity.PostTag;
import com.glody.glody_platform.community.entity.PostTagId;
import com.glody.glody_platform.community.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {
    List<PostTag> findByPost(Post post);
    List<PostTag> findByTag(Tag tag);
}
