package com.glody.glody_platform.expert.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {
    private Long userId;
    private Long expertId;
    private LocalDateTime appointmentTime;
}
