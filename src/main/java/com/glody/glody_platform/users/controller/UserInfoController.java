package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserInfoController {

    @GetMapping("/me/roles")
    @Operation(summary = "Lấy role của người dùng hiện tại")
    public List<String> getCurrentUserRoles(@AuthenticationPrincipal User currentUser) {
        return currentUser.getRoles().stream()
                .map(role -> "ROLE_" + role.getRoleName().toUpperCase())
                .toList();
    }
}
