package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.AuthRequestDto;
import com.glody.glody_platform.users.dto.AuthResponseDto;
import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.dto.RegistrationResponseDto;
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

    @Operation(summary = "Đăng nhập và nhận JWT token")
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

    @Operation(summary = "Đăng ký STUDENT (tự gán gói FREE)")
    @PostMapping("/register/student")
    public ResponseEntity<RegistrationResponseDto> registerStudent(
            @Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto, false);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(user));
    }

    @Operation(summary = "Đăng ký EXPERT (tự gán gói FREE)")
    @PostMapping("/register/expert")
    public ResponseEntity<RegistrationResponseDto> registerExpert(
            @Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto, true);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildResponse(user));
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
