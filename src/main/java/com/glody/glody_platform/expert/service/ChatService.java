package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.ChatContactDto;
import com.glody.glody_platform.expert.dto.ChatMessageDto;
import com.glody.glody_platform.expert.dto.ChatResponseDto;
import com.glody.glody_platform.expert.dto.SimpleUserDto;
import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.expert.repository.ChatRepository;
import com.glody.glody_platform.users.entity.Role;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatResponseDto sendMessage(long senderId,ChatMessageDto dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage(dto.getMessage());
        chat.setMessageType(dto.getMessageType());
        chat.setIsRead(false);

        chatRepository.save(chat);
        return toResponseDto(chat); // ✔️ trả về ChatResponseDto
    }


    public List<Chat> getConversation(Long user1Id, Long user2Id) {
        User u1 = userRepository.findById(user1Id).orElseThrow();
        User u2 = userRepository.findById(user2Id).orElseThrow();

        List<Chat> messages = new ArrayList<>();
        messages.addAll(chatRepository.findBySenderAndReceiver(u1, u2));
        messages.addAll(chatRepository.findByReceiverAndSender(u1, u2));
        messages.sort(Comparator.comparing(Chat::getCreatedAt));
        return messages;
    }

    public List<Chat> getUnreadMessages(Long receiverId) {
        return chatRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }

    public void markAsRead(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        chat.setIsRead(true);
        chatRepository.save(chat);
    }

    public void reactToMessage(Long chatId, String reaction) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        chat.setReaction(reaction);
        chatRepository.save(chat);
    }

    public List<ChatContactDto> getChatContacts(Long userId) {
        List<User> senders = chatRepository.findAllSendersToUser(userId);
        List<User> receivers = chatRepository.findAllReceiversFromUser(userId);

        Set<User> contactSet = new HashSet<>();
        contactSet.addAll(senders);
        contactSet.addAll(receivers);

        return contactSet.stream()
                .filter(u -> !u.getId().equals(userId)) // loại bỏ bản thân nếu có
                .map(this::toContactDto)
                .toList();
    }

    private ChatContactDto toContactDto(User user) {
        ChatContactDto dto = new ChatContactDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setRole(user.getRoles().stream().findFirst()
                .map(r -> r.getRoleName())
                .orElse("USER"));
        return dto;
    }


    public  ChatResponseDto toResponseDto(Chat chat) {
        ChatResponseDto dto = new ChatResponseDto();
        dto.setId(chat.getId());
        dto.setMessage(chat.getMessage());
        dto.setIsRead(chat.getIsRead());

        dto.setMessageType(chat.getMessageType());
        dto.setReaction(chat.getReaction());
        dto.setCreatedAt(chat.getCreatedAt());

        dto.setSender(toSimpleUser(chat.getSender()));
        dto.setReceiver(toSimpleUser(chat.getReceiver()));
        return dto;
    }

    private SimpleUserDto toSimpleUser(User user) {
        SimpleUserDto dto = new SimpleUserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setRole(
                user.getRoles().stream().findFirst().map(Role::getRoleName).orElse("USER")
        );
        return dto;
    }

}

