package com.glody.glody_platform.community.controller;

import com.glody.glody_platform.community.entity.Tag;
import com.glody.glody_platform.community.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAll();
    }
}
