package com.glody.glody_platform.community.controller;

import com.glody.glody_platform.community.dto.ReactionDto;
import com.glody.glody_platform.community.entity.Reaction;
import com.glody.glody_platform.community.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public Reaction react(@RequestBody ReactionDto dto) {
        return reactionService.react(dto);
    }
}
