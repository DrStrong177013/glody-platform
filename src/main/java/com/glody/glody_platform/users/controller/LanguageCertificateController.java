package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.LanguageCertificateDto;
import com.glody.glody_platform.users.service.LanguageCertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý chứng chỉ ngôn ngữ của người dùng.
 */
@RestController
@RequestMapping("/api/language-certificates")
@RequiredArgsConstructor
@Tag(name = "Language Certificate Controller", description = "Quản lý chứng chỉ ngôn ngữ của hồ sơ người dùng")
public class LanguageCertificateController {

    private final LanguageCertificateService certificateService;

    /**
     * Lấy danh sách chứng chỉ ngôn ngữ theo profile người dùng.
     *
     * @param profileId ID hồ sơ người dùng
     * @return Danh sách chứng chỉ
     */
    @Operation(summary = "Lấy danh sách chứng chỉ theo profile ID")
    @GetMapping("/user/{profileId}")
    public ResponseEntity<List<LanguageCertificateDto>> getCertificatesByProfile(@PathVariable Long profileId) {
        List<LanguageCertificateDto> certificates = certificateService.getCertificates(profileId);
        return ResponseEntity.ok(certificates);
    }

    /**
     * Thêm chứng chỉ mới cho hồ sơ người dùng.
     *
     * @param profileId ID hồ sơ
     * @param dto       Dữ liệu chứng chỉ
     * @return Thông báo thành công
     */
    @Operation(summary = "Thêm chứng chỉ ngôn ngữ vào hồ sơ người dùng")
    @PostMapping("/user/{profileId}")
    public ResponseEntity<String> addCertificate(
            @PathVariable Long profileId,
            @RequestBody LanguageCertificateDto dto) {

        certificateService.addCertificate(profileId, dto);
        return ResponseEntity.ok("📄 Chứng chỉ đã được thêm.");
    }

    /**
     * Xoá một chứng chỉ ngôn ngữ theo ID.
     *
     * @param certificateId ID chứng chỉ
     * @return Thông báo xoá thành công
     */
    @Operation(summary = "Xoá chứng chỉ ngôn ngữ theo ID")
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> deleteCertificate(@PathVariable Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        return ResponseEntity.ok("🗑️ Chứng chỉ đã bị xoá.");
    }
}
