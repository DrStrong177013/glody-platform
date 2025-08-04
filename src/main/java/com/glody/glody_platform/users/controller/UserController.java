package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.dto.UserSubscriptionPackageResponseDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.service.UserProfileService;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Quản lý cá nhân người dùng")
public class UserController {

    private final UserProfileService userProfileService;
    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @Operation(summary = "Lấy hồ sơ của chính mình")
    @GetMapping("/profile/me")
    public ResponseEntity<UserProfileDto> getMyProfile(
            @AuthenticationPrincipal User currentUser) {

        // currentUser đã được gán từ JwtAuthFilter
        Long userId = currentUser.getId();
        UserProfileDto profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Lưu hoặc cập nhật hồ sơ người dùng hiện tại")
    @PutMapping("/profile/me")
    public ResponseEntity<String> saveProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UserProfileDto dto) {

        Long userId = currentUser.getId();
        userProfileService.saveOrUpdate(userId, dto);
        return ResponseEntity.ok("Hồ sơ người dùng đã được lưu/cập nhật.");
    }
    @GetMapping("/me/roles")
    @Operation(summary = "Lấy role của người dùng hiện tại")
    public List<String> getCurrentUserRoles(@AuthenticationPrincipal User currentUser) {
        return currentUser.getRoles().stream()
                .map(role -> "ROLE_" + role.getRoleName().toUpperCase())
                .toList();
    }
    @Operation(
            summary = "Lấy tất cả gói đăng ký của người dùng",
            description = "Trả về danh sách tất cả các gói đăng ký mà người dùng hiện tại đã từng đăng ký, bao gồm cả các gói đã hết hạn hoặc đang inactive. Mỗi gói sẽ có thêm tên gói, ngày bắt đầu, ngày kết thúc, trạng thái active/expired."
    )
    @GetMapping("/subscriptions/all")
    public List<UserSubscriptionPackageResponseDto> getAllSubscriptions(@AuthenticationPrincipal User currentUser) {
        return userSubscriptionService.getAllSubscriptionsByUser(currentUser.getId());
    }

    @Operation(
            summary = "Lấy gói đăng ký đang active hiện tại của người dùng",
            description = "Trả về thông tin gói đăng ký đang còn hiệu lực (active) hiện tại của người dùng, bao gồm tên gói, ngày bắt đầu, ngày kết thúc, trạng thái active, v.v. Nếu không có gói nào active sẽ trả về null."
    )
    @GetMapping("/subscriptions/active")
    public UserSubscriptionPackageResponseDto getActiveSubscription(@AuthenticationPrincipal User currentUser) {
        return userSubscriptionService.getActiveSubscriptionByUser(currentUser.getId());
    }

}
