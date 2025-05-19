package com.glody.glody_platform.partner.dto;

import lombok.Data;

@Data
public class PartnerDto {
    private String name;
    private String description;
    private String website;
    private String logoUrl;
    private Long categoryId;
}
