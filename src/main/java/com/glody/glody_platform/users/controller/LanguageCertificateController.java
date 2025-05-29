package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.LanguageCertificateDto;
import com.glody.glody_platform.users.service.LanguageCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language-certificates")
@RequiredArgsConstructor
public class LanguageCertificateController {

    private final LanguageCertificateService certificateService;

    @GetMapping("/user/{profileId}")
    public List<LanguageCertificateDto> getCertificatesByProfile(@PathVariable Long profileId) {
        return certificateService.getCertificates(profileId);
    }

    @PostMapping("/user/{profileId}")
    public String addCertificate(@PathVariable Long profileId,
                                 @RequestBody LanguageCertificateDto dto) {
        certificateService.addCertificate(profileId, dto);
        return "Certificate added";
    }

    @DeleteMapping("/{certificateId}")
    public String deleteCertificate(@PathVariable Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        return "Certificate deleted";
    }
}
