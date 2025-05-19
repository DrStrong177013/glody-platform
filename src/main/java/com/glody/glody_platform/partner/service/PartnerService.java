package com.glody.glody_platform.partner.service;

import com.glody.glody_platform.partner.dto.PartnerDto;
import com.glody.glody_platform.partner.entity.Partner;
import com.glody.glody_platform.partner.entity.PartnerCategory;
import com.glody.glody_platform.partner.repository.PartnerCategoryRepository;
import com.glody.glody_platform.partner.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerCategoryRepository categoryRepository;

    public List<Partner> getAllPartners() {
        return partnerRepository.findByIsDeletedFalse();
    }

    public List<Partner> getPartnersByCategory(Long categoryId) {
        PartnerCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return partnerRepository.findByCategoryAndIsDeletedFalse(category);
    }

    public Partner create(PartnerDto dto) {
        PartnerCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Partner partner = new Partner();
        partner.setName(dto.getName());
        partner.setDescription(dto.getDescription());
        partner.setLogoUrl(dto.getLogoUrl());
        partner.setWebsite(dto.getWebsite());
        partner.setCategory(category);

        return partnerRepository.save(partner);
    }

    public void softDelete(Long id) {
        Partner p = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        p.setIsDeleted(true);
        p.setDeletedAt(LocalDateTime.now());
        partnerRepository.save(p);
    }
}
