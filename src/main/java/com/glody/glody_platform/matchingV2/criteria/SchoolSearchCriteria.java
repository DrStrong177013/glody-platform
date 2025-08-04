package com.glody.glody_platform.matchingV2.criteria;

import lombok.Data;

@Data
public class SchoolSearchCriteria {
    private String targetCountry;
    private String major;
    private String ranking; // ví dụ: "Top 100", hoặc "QS 500", v.v.
    private Double minGpa;  // nếu user muốn filter trường yêu cầu GPA không quá cao
    // Bạn có thể thêm location, loại trường, v.v.
}
