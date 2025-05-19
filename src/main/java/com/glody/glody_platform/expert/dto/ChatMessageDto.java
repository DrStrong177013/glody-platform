package com.glody.glody_platform.expert.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private Long senderId;
    private Long receiverId;
    private String message;
}
