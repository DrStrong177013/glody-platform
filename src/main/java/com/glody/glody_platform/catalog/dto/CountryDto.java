package com.glody.glody_platform.catalog.dto;

import com.glody.glody_platform.catalog.entity.Country;
import lombok.Data;

@Data
public class CountryDto {
    private Long id;
    private String name;

    public static CountryDto fromEntity(Country entity) {
        CountryDto dto = new CountryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public Country toEntity() {
        Country entity = new Country();
        entity.setId(id);
        entity.setName(name);
        return entity;
    }
}
