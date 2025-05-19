package com.glody.glody_platform.community.service;

import com.glody.glody_platform.community.dto.PostDto;
import com.glody.glody_platform.community.entity.*;
import com.glody.glody_platform.community.repository.*;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post create(PostDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        Post post = new Post();
        post.setUser(user);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostType(dto.getPostType());
        Post saved = postRepository.save(post);

        if (dto.getTags() != null) {
            for (String tagName : dto.getTags()) {
                Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });

                PostTag pt = new PostTag();
                pt.setPost(saved);
                pt.setTag(tag);
                postTagRepository.save(pt);
            }
        }

        return saved;
    }

    public List<Post> getAll() {
        return postRepository.findAll().stream()
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setIsDeleted(true);
        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
    }
}
