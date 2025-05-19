package com.glody.glody_platform.partner.service;

import com.glody.glody_platform.partner.entity.PartnerCategory;
import com.glody.glody_platform.partner.repository.PartnerCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerCategoryService {

    private final PartnerCategoryRepository partnerCategoryRepository;

    public List<PartnerCategory> getAllCategories() {
        return partnerCategoryRepository.findAll();
    }
}
