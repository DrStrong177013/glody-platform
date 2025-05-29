package com.glody.glody_platform.expert.dto;
import com.glody.glody_platform.expert.entity.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Dữ liệu để cập nhật trạng thái lịch hẹn")
@Data
public class AppointmentStatusUpdateDto {

    @Schema(description = "Trạng thái mới", example = "CONFIRMED")
    private AppointmentStatus status;
}
