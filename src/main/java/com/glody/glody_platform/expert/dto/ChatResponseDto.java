package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Thông tin phản hồi khi gửi hoặc nhận tin nhắn")
public class ChatResponseDto {

    @Schema(description = "ID tin nhắn", example = "6")
    private Long id;

    @Schema(description = "Nội dung tin nhắn", example = "Em cần tư vấn về du học Đài Loan")
    private String message;

    @Schema(description = "Tin nhắn đã đọc chưa", example = "false")
    private Boolean isRead;

    @Schema(description = "Có phải từ chuyên gia không", example = "true")
    private Boolean fromExpert;

    @Schema(description = "Loại tin nhắn: TEXT, IMAGE, FILE", example = "TEXT")
    private String messageType;

    @Schema(description = "Biểu tượng cảm xúc đính kèm", example = "❤️", nullable = true)
    private String reaction;

    @Schema(description = "Thời gian gửi tin nhắn", example = "2025-05-30T14:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Người gửi")
    private SimpleUserDto sender;

    @Schema(description = "Người nhận")
    private SimpleUserDto receiver;
}
