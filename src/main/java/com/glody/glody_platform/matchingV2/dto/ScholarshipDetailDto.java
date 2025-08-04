package com.glody.glody_platform.matchingV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScholarshipDetailDto {
    private Long id;
    private String title;
    private String sponsor;
    private String value; // Giá trị học bổng (toàn phần/bán phần/tiền mặt...)
    private String description;
    private String applicationDeadline;
    private String schoolName;
    private String country;
    private List<String> conditions; // Các điều kiện học bổng (GPA, ngoại ngữ, ...)

}