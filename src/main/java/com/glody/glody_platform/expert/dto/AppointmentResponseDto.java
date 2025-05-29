package com.glody.glody_platform.expert.dto;


import com.glody.glody_platform.expert.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Long id;
    private String userFullName;
    private String expertFullName;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
