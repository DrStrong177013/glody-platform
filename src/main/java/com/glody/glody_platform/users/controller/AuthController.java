
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
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Xác thực và tạo token cho người dùng")
public class AuthController {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Đăng nhập và nhận JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        try {
            User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email không tồn tại hoặc đã bị vô hiệu hóa"));

            String rawPassword = request.getPassword();
            String storedHash = user.getPasswordHash();

            System.out.println("Testing password variations:");
            System.out.println("1. Original: " + passwordEncoder.matches(rawPassword, storedHash));
            System.out.println("2. Trimmed: " + passwordEncoder.matches(rawPassword.trim(), storedHash));
            System.out.println("3. No special chars: " + passwordEncoder.matches(rawPassword.replaceAll("[^a-zA-Z0-9]", ""), storedHash));

            // Thử với một số mật khẩu thông dụng để kiểm tra
            String[] commonVariations = {
                    "string_1", // lowercase
                    "STRING_1", // uppercase
                    "String1",  // no underscore
                    "String-1"  // with hyphen
            };

            for (int i = 0; i < commonVariations.length; i++) {
                System.out.println("Variation " + (i+4) + ": " +
                        passwordEncoder.matches(commonVariations[i], storedHash));
            }

            if (!passwordEncoder.matches(rawPassword, storedHash)) {
                throw new BadCredentialsException("Mật khẩu không chính xác");
            }




            List<String> roleNames = user.getRoles().stream()
                    .map(role -> "ROLE_" + role.getRoleName().toUpperCase())
                    .toList();

            String token = jwtTokenUtil.generateToken(String.valueOf(user.getId()),
                    user.getEmail(),
                    roleNames);

            String fullName = userProfileRepository.findByUserId(user.getId())
                    .map(UserProfile::getFullName)
                    .orElse("Không rõ");

            return ResponseEntity.ok(new AuthResponseDto(token, user.getEmail(), fullName));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Có lỗi xảy ra trong quá trình xác thực"));
        }
    }
    @PostConstruct
    public void testPasswordEncoder() {
        String rawPassword = "String_1";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Test password encoding:");
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
        System.out.println("Matches with stored: " +
                passwordEncoder.matches(rawPassword,
                        "$2a$12$e9N6hKx2QpZ/3jm1hWcOduCunadPiI6bxzCvF1Y9XyGZ7vYl8Tzne"));
    }

}

@Getter
@AllArgsConstructor
class ErrorResponse {
    private String message;
}