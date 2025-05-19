package com.glody.glody_platform.community.controller;

import com.glody.glody_platform.community.dto.CommentDto;
import com.glody.glody_platform.community.entity.Comment;
import com.glody.glody_platform.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Comment addComment(@RequestBody CommentDto dto) {
        return commentService.create(dto);
    }

    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }
}
