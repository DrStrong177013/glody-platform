package com.glody.glody_platform.expert.dto;

import lombok.Data;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dữ liệu để người dùng có tài khoản đặt lịch hẹn với chuyên gia")
@Data
public class AppointmentRequestDto {

    @Schema(description = "ID của chuyên gia nhận lịch", example = "2")
    private Long expertId;

    @Schema(description = "Thời gian cuộc hẹn (yyyy-MM-ddTHH:mm:ss)", example = "2025-06-01T10:00:00")
    private LocalDateTime appointmentTime;
}


