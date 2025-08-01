package com.glody.glody_platform.expert.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chats")
@Getter
@Setter
public class Chat extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String message;

    @Column(nullable = false)
    private Boolean isRead = false;

    private String messageType = "TEXT"; // TEXT, IMAGE, FILE

    private String reaction; // ❤️, 👍, etc.
}

