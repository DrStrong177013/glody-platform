package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Dữ liệu gửi tin nhắn giữa người dùng và chuyên gia")
@Data
public class ChatMessageDto {


    @Schema(description = "ID người nhận", example = "6")
    private Long receiverId;

    @Schema(description = "Nội dung tin nhắn", example = "Em cần tư vấn về hồ sơ du học Đài Loan ạ")
    private String message;

    @Schema(description = "Loại tin nhắn: TEXT, IMAGE, FILE", example = "TEXT", defaultValue = "TEXT")
    private String messageType = "TEXT";
}
