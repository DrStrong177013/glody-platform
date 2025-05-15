package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.AuthRequestDto;
import com.glody.glody_platform.users.dto.AuthResponseDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenUtil.generateToken(String.valueOf(user.getId()), user.getEmail());

        return new AuthResponseDto(token, user.getEmail(), user.getFullName());
    }
}