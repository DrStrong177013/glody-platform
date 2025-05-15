package com.glody.glody_platform.academic.dto;
import com.glody.glody_platform.academic.entity.*;
import lombok.Data;
import java.util.List;

@Data
public class UniversityDto {
    private Long id;
    private String name;
    private Long countryId;

    public static UniversityDto fromEntity(University entity) {
        UniversityDto dto = new UniversityDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCountryId(entity.getCountry().getId());
        return dto;
    }

    public University toEntity() {
        University entity = new University();
        entity.setId(id);
        entity.setName(name);
        Country country = new Country();
        country.setId(countryId);
        entity.setCountry(country);
        return entity;
    }
}
