package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
@Tag(name = "User Profile Controller", description = "Quản lý hồ sơ người dùng")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    private Long getUserIdFromAuth(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return user.getId();
    }

    @Operation(summary = "Lấy hồ sơ của chính mình")
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        UserProfileDto profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Lưu hoặc cập nhật hồ sơ người dùng hiện tại")
    @PutMapping("/me")
    public ResponseEntity<String> saveProfile(
            Authentication authentication,
            @RequestBody UserProfileDto dto) {

        Long userId = getUserIdFromAuth(authentication);
        userProfileService.saveOrUpdate(userId, dto);
        return ResponseEntity.ok("📄 Hồ sơ người dùng đã được lưu/cập nhật.");
    }
}
