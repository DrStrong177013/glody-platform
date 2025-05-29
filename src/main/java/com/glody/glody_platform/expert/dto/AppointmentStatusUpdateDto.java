package com.glody.glody_platform.expert.dto;
import com.glody.glody_platform.expert.entity.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentStatusUpdateDto {
    private AppointmentStatus status;
}
