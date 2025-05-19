package com.glody.glody_platform.partner.controller;

import com.glody.glody_platform.partner.dto.PartnerClickDto;
import com.glody.glody_platform.partner.entity.PartnerClick;
import com.glody.glody_platform.partner.service.PartnerClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partner-clicks")
@RequiredArgsConstructor
public class PartnerClickController {

    private final PartnerClickService clickService;

    @PostMapping
    public ResponseEntity<PartnerClick> logClick(@RequestBody PartnerClickDto dto) {
        return ResponseEntity.ok(clickService.logClick(dto));
    }
}
