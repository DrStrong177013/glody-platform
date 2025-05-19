package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.ChatMessageDto;
import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.expert.repository.ChatRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional
    public Chat sendMessage(ChatMessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage(dto.getMessage());

        return chatRepository.save(chat);
    }

    public List<Chat> getConversation(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        List<Chat> messages = new ArrayList<>();
        messages.addAll(chatRepository.findBySenderAndReceiver(user1, user2));
        messages.addAll(chatRepository.findByReceiverAndSender(user1, user2));
        messages.sort((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
        return messages;
    }
}
