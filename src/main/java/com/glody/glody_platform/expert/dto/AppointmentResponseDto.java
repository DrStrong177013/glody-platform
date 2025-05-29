package com.glody.glody_platform.expert.dto;


import com.glody.glody_platform.expert.entity.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Thông tin trả về của một lịch hẹn")
@Data
public class AppointmentResponseDto {

    @Schema(description = "ID của lịch hẹn", example = "101")
    private Long id;

    @Schema(description = "Thời gian hẹn", example = "2025-06-01T14:00:00")
    private LocalDateTime appointmentTime;

    @Schema(description = "Trạng thái hiện tại", example = "PENDING")
    private AppointmentStatus status;

    @Schema(description = "Tên người đặt lịch", example = "Nguyễn Văn A")
    private String userFullName;

    @Schema(description = "Tên chuyên gia nhận lịch", example = "Trần Đức Anh")
    private String expertFullName;
}
