package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.*;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import com.glody.glody_platform.users.service.UserService;
import com.glody.glody_platform.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Đăng ký, xác thực và tạo token")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Đăng nhập và nhận JWT token",
    description = "Tài khoản đăng nhập mẫu (mk:String_1): " +
            "  bob.expert@goldyai.com  (ROLE_EXPERT, gói FREE), " +
            "  alice@gmail.com  (ROLE_STUDENT, gói FREE), " +
            "  diana@gmail.com (ROLE_STUDENT, gói PREMIUM),  " +
            "  edward@gmail.com (ROLE_STUDENT, gói BASIC), " +
            "  charlie@gmail.com (ROLE_STUDENT, gói FREE) ")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        try {
            User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email không tồn tại hoặc đã bị vô hiệu hóa"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new BadCredentialsException("Mật khẩu không chính xác");
            }

            List<String> roles = user.getRoles().stream()
                    .map(r -> "ROLE_" + r.getRoleName().toUpperCase())
                    .toList();
            String token = jwtTokenUtil.generateToken(
                    String.valueOf(user.getId()),
                    user.getEmail(),
                    roles
            );
            String fullName = userProfileRepository.findByUserId(user.getId())
                    .map(UserProfile::getFullName).orElse("Không rõ");

            return ResponseEntity.ok(new AuthResponseDto(token, user.getEmail(), fullName));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Có lỗi trong quá trình xác thực"));
        }
    }

    @Operation(summary = "Đăng ký STUDENT", description = "Đăng ký xong sẽ tự gán package FREE và tạo hồ sơ cho người dùng")
    @PostMapping("/register/student")
    public ResponseEntity<RegistrationResponseDto> registerStudent(
            @Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto, false);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(user));
    }

    @Operation(summary = "Đăng ký EXPERT ", description = "Đăng ký xong sẽ tự gán package FREE và tạo hồ sơ cho người dùng")
    @PostMapping("/register/expert")
    public ResponseEntity<RegistrationResponseDto> registerExpert(
            @Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto, true);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(user));
    }

    @Operation(summary = "Quên Mật khẩu, lấy lại với Email", description = "Nếu email hợp lệ, hướng dẫn reset sẽ được gửi đến email.")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.sendForgotPasswordEmail(request.getEmail());
        return ResponseEntity.ok("Nếu email hợp lệ, hướng dẫn reset sẽ được gửi đến email.");
    }

    @Operation(summary = "Đổi mật khẩu", description = "Đổi mật khẩu với resetToken. Token lấy ở trên thanh URL" +
            " sau khi bấm vào đường link RestPassword gửi ở email (thông qua api forgot-password)")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean result = userService.resetPassword(request.getToken(), request.getNewPassword());
        if (result) {
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } else {
            return ResponseEntity.badRequest().body("Token không hợp lệ hoặc đã hết hạn!");
        }
    }

    private RegistrationResponseDto buildResponse(User user) {
        RegistrationResponseDto dto = new RegistrationResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRoles(user.getRoles().stream()
                .map(r -> r.getRoleName())
                .toList());

        userSubscriptionRepository
                .findAllByUserIdAndIsDeletedFalse(user.getId())
                .stream()
                .filter(UserSubscription::getIsActive)
                .max(Comparator.comparing(UserSubscription::getEndDate))
                .ifPresent(sub -> {
                    dto.setDefaultPackageName(sub.getSubscriptionPackage().getName());
                    dto.setDefaultPackageEndDate(sub.getEndDate());
                });

        return dto;
    }

}

@Getter
@AllArgsConstructor
class ErrorResponse {
    private String message;
}
