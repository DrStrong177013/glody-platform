package com.glody.glody_platform.community.service;

import com.glody.glody_platform.community.dto.CommentDto;
import com.glody.glody_platform.community.entity.Comment;
import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.community.repository.CommentRepository;
import com.glody.glody_platform.community.repository.PostRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment create(CommentDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(dto.getContent());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentRepository.findByPostAndIsDeletedFalse(post);
    }
}
