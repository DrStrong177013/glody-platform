package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ChatContactDto;
import com.glody.glody_platform.expert.dto.ChatMessageDto;
import com.glody.glody_platform.expert.dto.ChatResponseDto;
import com.glody.glody_platform.expert.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý chức năng nhắn tin giữa người dùng.
 */
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@Tag(name = "Chat Controller", description = "Quản lý tin nhắn và trò chuyện giữa người dùng")
public class ChatController {

    private final ChatService chatService;

    /**
     * Gửi tin nhắn giữa hai người dùng.
     *
     * @param dto Nội dung tin nhắn
     * @return Tin nhắn đã gửi (ChatResponseDto)
     */
    @Operation(summary = "Gửi tin nhắn")
    @PostMapping
    public ResponseEntity<ChatResponseDto> sendMessage(@RequestBody ChatMessageDto dto) {
        ChatResponseDto response = chatService.sendMessage(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Lấy toàn bộ cuộc trò chuyện giữa 2 người dùng theo userId.
     *
     * @param user1Id ID người dùng 1
     * @param user2Id ID người dùng 2
     * @return Danh sách tin nhắn trong cuộc trò chuyện
     */
    @Operation(summary = "Lấy cuộc trò chuyện giữa 2 người dùng")
    @GetMapping("/conversation")
    public ResponseEntity<List<ChatResponseDto>> getConversation(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id) {

        List<ChatResponseDto> messages = chatService.getConversation(user1Id, user2Id).stream()
                .map(chatService::toResponseDto)
                .toList();

        return ResponseEntity.ok(messages);
    }

    /**
     * Lấy tin nhắn chưa đọc của một người dùng (receiver).
     *
     * @param receiverId ID người nhận
     * @return Danh sách tin nhắn chưa đọc
     */
    @Operation(summary = "Lấy tin nhắn chưa đọc của người dùng")
    @GetMapping("/unread")
    public ResponseEntity<List<ChatResponseDto>> getUnreadMessages(@RequestParam Long receiverId) {
        List<ChatResponseDto> unreadMessages = chatService.getUnreadMessages(receiverId).stream()
                .map(chatService::toResponseDto)
                .toList();

        return ResponseEntity.ok(unreadMessages);
    }

    /**
     * Đánh dấu một tin nhắn là đã đọc.
     *
     * @param id ID tin nhắn
     * @return Thông báo đã đánh dấu
     */
    @Operation(summary = "Đánh dấu tin nhắn đã đọc")
    @PatchMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        chatService.markAsRead(id);
        return ResponseEntity.ok("📩 Tin nhắn đã được đánh dấu là đã đọc.");
    }

    /**
     * Gửi biểu tượng cảm xúc (reaction) đến tin nhắn.
     *
     * @param id       ID tin nhắn
     * @param reaction Biểu tượng cảm xúc (ví dụ: ❤)
     * @return Phản hồi thành công
     */
    @Operation(summary = "Gửi biểu tượng cảm xúc (reaction ❤️)", description = "Reaction như: ❤, 😂, 👍,...")
    @PatchMapping("/{id}/reaction")
    public ResponseEntity<String> reactToMessage(
            @PathVariable Long id,
            @RequestParam String reaction) {

        chatService.reactToMessage(id, reaction);
        return ResponseEntity.ok("😊 Đã gửi reaction.");
    }

    /**
     * Lấy danh sách người dùng đã từng trò chuyện với userId.
     *
     * @param userId ID người dùng hiện tại
     * @return Danh sách liên hệ trò chuyện
     */
    @Operation(summary = "Lấy danh sách người dùng đã từng nhắn tin với userId")
    @GetMapping("/contacts/{userId}")
    public ResponseEntity<List<ChatContactDto>> getContacts(@PathVariable Long userId) {
        List<ChatContactDto> contacts = chatService.getChatContacts(userId);
        return ResponseEntity.ok(contacts);
    }
}
