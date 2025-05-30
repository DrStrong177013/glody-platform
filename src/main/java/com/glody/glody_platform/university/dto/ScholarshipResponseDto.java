package com.glody.glody_platform.university.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ScholarshipResponseDto {
    private Long id;
    private String title;
    private String sponsor;
    private String description;
    private String value;
    private LocalDate applicationDeadline;
    private List<String> conditions;
    private String schoolName;
}
