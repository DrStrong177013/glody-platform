package com.glody.glody_platform.expert.dto;

import lombok.Data;

@Data
public class ConsultationNoteDto {
    private Long appointmentId;
    private Long expertId;
    private String note;
}
