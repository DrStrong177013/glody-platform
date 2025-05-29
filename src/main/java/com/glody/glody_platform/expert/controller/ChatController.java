package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ChatMessageDto;
import com.glody.glody_platform.expert.dto.ChatResponseDto;
import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.expert.service.ChatService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Gửi tin nhắn")
    @PostMapping
    public ChatResponseDto sendMessage(@RequestBody ChatMessageDto dto) {
        return chatService.sendMessage(dto); // ✔️ Trả về DTO đã rút gọn
    }


    @Operation(summary = "Lấy cuộc trò chuyện giữa 2 người dùng")
    @GetMapping("/conversation")
    public List<ChatResponseDto> getConversation(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return chatService.getConversation(user1Id, user2Id).stream()
                .map(chatService::toResponseDto) // 👈 Gọi đúng từ service
                .toList();
    }


    @Operation(summary = "Lấy tin nhắn chưa đọc của người dùng")
    @GetMapping("/unread")
    public List<ChatResponseDto> getUnread(@RequestParam Long receiverId) {
        return chatService.getUnreadMessages(receiverId).stream()
                .map(chatService::toResponseDto)
                .toList();
    }


    @Operation(summary = "Đánh dấu tin nhắn đã đọc")
    @PatchMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        chatService.markAsRead(id);
        return ResponseEntity.ok("Tin nhắn đã được đánh dấu là đã đọc.");
    }

    @Operation(summary = "Gửi biểu tượng cảm xúc (reaction ❤\uFE0F)",
            description ="reaction ❤\uFE0F")

    @PatchMapping("/{id}/reaction")
    public ResponseEntity<String> reactToMessage(@PathVariable Long id, @RequestParam String reaction) {
        chatService.reactToMessage(id, reaction);
        return ResponseEntity.ok("Đã gửi reaction.");
    }

    @Operation(summary = "Lấy danh sách người dùng đã từng nhắn tin với userId")
    @GetMapping("/contacts/{userId}")
    public List<User> getContacts(@PathVariable Long userId) {
        return chatService.getChatContacts(userId);
    }
}

