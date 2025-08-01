package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Dữ liệu để người dùng ẩn danh đặt lịch với chuyên gia")
@Data
public class AppointmentBookingDto {

    @Schema(description = "Họ tên người đặt lịch", example = "Nguyễn Văn Tiến")
    private String fullName;

    @Schema(description = "Email liên hệ", example = "nguyenvantien@gmail.com")
    private String email;

    @Schema(description = "Số điện thoại", example = "0912345678")
    private String phone;

    @Schema(description = "ID của chuyên gia nhận lịch(Bob)", example = "2")
    private Long expertId;

    @Schema(description = "Thời gian cuộc hẹn (yyyy-MM-ddTHH:mm:ss)", example = "2025-09-01T14:00:00")
    private LocalDateTime appointmentDateTime;
}
