package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.LanguageCertificateRequest;
import com.glody.glody_platform.users.dto.LanguageCertificateResponse;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.LanguageCertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language-certificates")
@RequiredArgsConstructor
@Tag(name = "Language Certificate Controller", description = "Quản lý chứng chỉ ngôn ngữ của hồ sơ người dùng")
public class LanguageCertificateController {

    private final LanguageCertificateService certificateService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    private Long getProfileIdFromAuth(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"))
                .getId();
    }

    @Operation(summary = "Lấy danh sách chứng chỉ của người dùng hiện tại")
    @GetMapping
    public ResponseEntity<List<LanguageCertificateResponse>> getCertificates(Authentication authentication) {
        Long profileId = getProfileIdFromAuth(authentication);
        List<LanguageCertificateResponse> certificates = certificateService.getCertificates(profileId);
        return ResponseEntity.ok(certificates);
    }

    @Operation(summary = "Thêm chứng chỉ ngôn ngữ vào hồ sơ người dùng hiện tại")
    @PostMapping
    public ResponseEntity<String> addCertificate(
            Authentication authentication,
            @Valid @RequestBody LanguageCertificateRequest request) {

        Long profileId = getProfileIdFromAuth(authentication);
        certificateService.addCertificate(profileId, request);
        return ResponseEntity.ok("📄 Chứng chỉ đã được thêm.");
    }

    @Operation(summary = "Xoá chứng chỉ ngôn ngữ theo ID")
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> deleteCertificate(@PathVariable Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        return ResponseEntity.ok("🗑️ Chứng chỉ đã bị xoá.");
    }

    @Operation(summary = "Cập nhật thông tin chứng chỉ ngôn ngữ")
    @PutMapping("/{certificateId}")
    public ResponseEntity<String> updateCertificate(
            @PathVariable Long certificateId,
            @Valid @RequestBody LanguageCertificateRequest request) {

        certificateService.updateCertificate(certificateId, request);
        return ResponseEntity.ok("✏️ Chứng chỉ đã được cập nhật.");
    }
}
