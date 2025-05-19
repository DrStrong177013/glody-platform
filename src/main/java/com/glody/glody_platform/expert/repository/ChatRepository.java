package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderAndReceiver(User sender, User receiver);
    List<Chat> findByReceiverAndSender(User receiver, User sender);
}
