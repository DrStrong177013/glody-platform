package com.glody.glody_platform.community.controller;

import com.glody.glody_platform.community.dto.PostDto;
import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Post createPost(@RequestBody PostDto dto) {
        return postService.create(dto);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAll();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("Post soft-deleted successfully");
    }
}
