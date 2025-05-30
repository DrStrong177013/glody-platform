package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller quản lý hồ sơ người dùng.
 */
@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
@Tag(name = "User Profile Controller", description = "Quản lý hồ sơ người dùng")
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * Lấy thông tin hồ sơ người dùng theo ID.
     *
     * @param userId ID người dùng
     * @return Hồ sơ người dùng
     */
    @Operation(summary = "Lấy hồ sơ của chính mình (theo userId)")
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(@RequestParam Long userId) {
        UserProfileDto profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Cập nhật hoặc tạo mới hồ sơ người dùng.
     *
     * @param userId ID người dùng
     * @param dto    Dữ liệu hồ sơ
     * @return Thông báo thành công
     */
    @Operation(summary = "Lưu hoặc cập nhật hồ sơ người dùng")
    @PutMapping
    public ResponseEntity<String> saveProfile(
            @RequestParam Long userId,
            @RequestBody UserProfileDto dto) {

        userProfileService.saveOrUpdate(userId, dto);
        return ResponseEntity.ok("📄 Hồ sơ người dùng đã được lưu/cập nhật.");
    }
}
