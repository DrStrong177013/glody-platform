package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.AuthRequestDto;
import com.glody.glody_platform.users.dto.AuthResponseDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller xử lý xác thực người dùng (authentication).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Xác thực và tạo token cho người dùng")
public class AuthController {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * API đăng nhập, kiểm tra thông tin đăng nhập và trả về JWT token.
     *
     * @param request Thông tin email và mật khẩu
     * @return Token kèm email và họ tên người dùng
     */
    @Operation(summary = "Đăng nhập và nhận JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Thông tin đăng nhập không đúng.");
        }

        String token = jwtTokenUtil.generateToken(String.valueOf(user.getId()), user.getEmail());

        String fullName = userProfileRepository.findByUserId(user.getId())
                .map(UserProfile::getFullName)
                .orElse("Không rõ");

        AuthResponseDto response = new AuthResponseDto(token, user.getEmail(), fullName);
        return ResponseEntity.ok(response);
    }
}
