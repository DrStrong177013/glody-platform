package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.service.ExpertProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expert-profiles")
@RequiredArgsConstructor
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;
    @GetMapping("/{Id}")
    public ResponseEntity<ExpertProfileDto> getExpertProfile(@PathVariable Long userId) {
        ExpertProfileDto dto = expertProfileService.getExpertProfileByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<String> updateExpertProfile(@PathVariable Long userId,
                                                      @RequestBody ExpertProfileUpdateDto dto) {
        expertProfileService.updateExpertProfile(userId, dto);
        return ResponseEntity.ok("Expert profile updated successfully");
    }
}