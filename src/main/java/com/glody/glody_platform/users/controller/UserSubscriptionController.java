package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserSubscriptionDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping("/active")
    public UserSubscriptionDto getActive(@RequestParam Long userId) {
        return userSubscriptionService.getActiveSubscription(userId);
    }

    @PostMapping
    public String subscribe(@RequestParam Long userId, @RequestBody UserSubscriptionDto dto) {
        userSubscriptionService.createSubscription(userId, dto);
        return "User subscribed";
    }
}