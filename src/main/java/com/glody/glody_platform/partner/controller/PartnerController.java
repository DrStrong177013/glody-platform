package com.glody.glody_platform.partner.controller;

import com.glody.glody_platform.partner.dto.PartnerDto;
import com.glody.glody_platform.partner.entity.Partner;
import com.glody.glody_platform.partner.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }

    @GetMapping("/category/{categoryId}")
    public List<Partner> getPartnersByCategory(@PathVariable Long categoryId) {
        return partnerService.getPartnersByCategory(categoryId);
    }

    @PostMapping
    public Partner createPartner(@RequestBody PartnerDto dto) {
        return partnerService.create(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePartner(@PathVariable Long id) {
        partnerService.softDelete(id);
        return ResponseEntity.ok("Partner soft deleted successfully");
    }
}
