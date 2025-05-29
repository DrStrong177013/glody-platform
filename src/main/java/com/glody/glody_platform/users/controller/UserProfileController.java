package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public UserProfileDto getMyProfile(@RequestParam Long userId) {
        return userProfileService.getProfile(userId);
    }

    @PutMapping
    public String saveProfile(@RequestParam Long userId, @RequestBody UserProfileDto dto) {
        userProfileService.saveOrUpdate(userId, dto);
        return "User profile saved/updated";
    }
}

