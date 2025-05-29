package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.Chat;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findBySenderAndReceiver(User sender, User receiver);

    List<Chat> findByReceiverAndSender(User receiver, User sender);

    List<Chat> findByReceiverIdAndIsReadFalse(Long receiverId);

    @Query("""
        SELECT DISTINCT CASE 
            WHEN c.sender.id = :userId THEN c.receiver 
            ELSE c.sender 
        END 
        FROM Chat c 
        WHERE c.sender.id = :userId OR c.receiver.id = :userId
        """)
    List<User> findChatContacts(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c.sender FROM Chat c WHERE c.receiver.id = :userId")
    List<User> findAllSendersToUser(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c.receiver FROM Chat c WHERE c.sender.id = :userId")
    List<User> findAllReceiversFromUser(@Param("userId") Long userId);

}

