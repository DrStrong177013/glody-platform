package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.service.ExpertProfileService;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller xử lý thao tác với hồ sơ chuyên gia (expert profile).
 */
@Hidden
@RestController
@RequestMapping("/api/expert-profiles")
@RequiredArgsConstructor
@Tag(name = "Expert Profile Controller", description = "Xem và cập nhật hồ sơ chuyên gia")
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;
    private final UserRepository userRepository;

    private Long getUserIdFromAuth(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return user.getId();
    }

    @Operation(summary = "Lấy thông tin hồ sơ chuyên gia của chính mình (Expert)")
    @GetMapping("/me")
    public ResponseEntity<ExpertProfileDto> getExpertProfile(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        ExpertProfileDto dto = expertProfileService.getExpertProfileByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Cập nhật thông tin hồ sơ chuyên gia của chính mình (Expert)")
    @PutMapping("/me")
    public ResponseEntity<String> updateExpertProfile(
            Authentication authentication,
            @RequestBody ExpertProfileUpdateDto dto) {

        Long userId = getUserIdFromAuth(authentication);
        expertProfileService.updateExpertProfile(userId, dto);
        return ResponseEntity.ok("📝 Hồ sơ chuyên gia đã được cập nhật thành công.");
    }
}
