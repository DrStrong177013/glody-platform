package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.service.ExpertProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller xử lý thao tác với hồ sơ chuyên gia (expert profile).
 */
@RestController
@RequestMapping("/api/expert-profiles")
@RequiredArgsConstructor
@Tag(name = "Expert Profile Controller", description = "Xem và cập nhật hồ sơ chuyên gia")
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;

    /**
     * Lấy thông tin hồ sơ chuyên gia theo userId.
     *
     * @param userId ID của người dùng chuyên gia
     * @return Dữ liệu hồ sơ chuyên gia
     */
    @Operation(summary = "Lấy thông tin hồ sơ chuyên gia")
    @GetMapping("/{userId}")
    public ResponseEntity<ExpertProfileDto> getExpertProfile(@PathVariable Long userId) {
        ExpertProfileDto dto = expertProfileService.getExpertProfileByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Cập nhật thông tin hồ sơ chuyên gia.
     *
     * @param userId ID người dùng chuyên gia
     * @param dto    Dữ liệu cần cập nhật
     * @return Thông báo cập nhật thành công
     */
    @Operation(summary = "Cập nhật thông tin hồ sơ chuyên gia")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateExpertProfile(
            @PathVariable Long userId,
            @RequestBody ExpertProfileUpdateDto dto) {

        expertProfileService.updateExpertProfile(userId, dto);
        return ResponseEntity.ok("📝 Hồ sơ chuyên gia đã được cập nhật thành công.");
    }
}
