package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Dữ liệu gửi tin nhắn giữa người dùng và chuyên gia")
@Data
public class ChatMessageDto {

    @Schema(description = "ID người gửi", example = "3")
    private Long senderId;

    @Schema(description = "ID người nhận", example = "7")
    private Long receiverId;

    @Schema(description = "Nội dung tin nhắn", example = "Em cần tư vấn về hồ sơ du học Đài Loan ạ")
    private String message;

    @Schema(description = "Tin nhắn gửi từ chuyên gia hay không", example = "false", defaultValue = "false")
    private Boolean fromExpert = false;

    @Schema(description = "Loại tin nhắn: TEXT, IMAGE, FILE", example = "TEXT", defaultValue = "TEXT")
    private String messageType = "TEXT";
}
