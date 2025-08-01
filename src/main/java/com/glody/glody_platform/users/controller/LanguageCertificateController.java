package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.LanguageCertificateRequest;
import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.service.LanguageCertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language-certificates")
@RequiredArgsConstructor
@Tag(name = "Language Certificate Controller", description = "Quản lý chứng chỉ ngôn ngữ của hồ sơ người dùng")
public class LanguageCertificateController {

    private final LanguageCertificateService certificateService;
    private final UserProfileRepository userProfileRepository;

    @Operation(summary = "Lấy danh sách chứng chỉ của người dùng hiện tại (Student)")
    @GetMapping
    public ResponseEntity<List<LanguageCertificateResponse>> getCertificates(
            @AuthenticationPrincipal User currentUser) {

        Long profileId = userProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"))
                .getId();

        List<LanguageCertificateResponse> certificates =
                certificateService.getCertificates(profileId);
        return ResponseEntity.ok(certificates);
    }

    @Operation(summary = "Thêm chứng chỉ ngôn ngữ vào hồ sơ người dùng hiện tại (Student)")
    @PostMapping
    public ResponseEntity<String> addCertificate(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody LanguageCertificateRequest request) {

        Long profileId = userProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"))
                .getId();

        certificateService.addCertificate(profileId, request);
        return ResponseEntity.ok("📄 Chứng chỉ đã được thêm.");
    }

    @Operation(summary = "Xoá chứng chỉ ngôn ngữ theo ID (Student)")
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> deleteCertificate(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long certificateId) {

        // nếu cần: kiểm tra certificateId có thuộc về profileId của currentUser
        certificateService.deleteCertificate(certificateId);
        return ResponseEntity.ok("🗑️ Chứng chỉ đã bị xoá.");
    }

    @Operation(summary = "Cập nhật thông tin chứng chỉ ngôn ngữ (Student)")
    @PutMapping("/{certificateId}")
    public ResponseEntity<String> updateCertificate(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long certificateId,
            @Valid @RequestBody LanguageCertificateRequest request) {

        certificateService.updateCertificate(certificateId, request);
        return ResponseEntity.ok("✏️ Chứng chỉ đã được cập nhật.");
    }
}
