package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ChatMessageDto;
import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.expert.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Chat sendMessage(@RequestBody ChatMessageDto dto) {
        return chatService.sendMessage(dto);
    }

    @GetMapping
    public List<Chat> getConversation(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return chatService.getConversation(user1Id, user2Id);
    }
}
