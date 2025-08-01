package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.dto.ExpertProfileUpdateDto;
import com.glody.glody_platform.expert.service.ExpertProfileService;
import com.glody.glody_platform.expert.service.ExpertService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@Tag(name = "Expert Controller", description = "Tìm kiếm và quản lý chuyên gia")
@Slf4j
public class ExpertController {

    private final ExpertService expertService;
    private final ExpertProfileService expertProfileService;

    @Operation(summary = "Tìm kiếm danh sách chuyên gia")
    @GetMapping
    public ResponseEntity<Page<ExpertProfileDto>> searchExperts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0")     int    page,
            @RequestParam(defaultValue = "6")     int    size,
            @RequestParam(defaultValue = "id")    String sortBy,
            @RequestParam(defaultValue = "asc")   String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        log.info("Searching experts: keyword='{}', page={}, size={}, sortBy={}, direction={}",
                keyword, page, size, sortBy, direction);

        Page<ExpertProfileDto> results = expertService.getExperts(keyword, pageable);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Lấy hồ sơ chuyên gia của chính mình (Expert)")
    @GetMapping("/me")
    public ResponseEntity<ExpertProfileDto> getMyProfile(
            @AuthenticationPrincipal User currentUser
    ) {
        log.info("Fetching expert profile for userId={}", currentUser.getId());
        ExpertProfileDto dto = expertProfileService.getExpertProfileByUserId(currentUser.getId());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Cập nhật hồ sơ chuyên gia của chính mình (Expert)")
    @PutMapping("/me")
    public ResponseEntity<String> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody ExpertProfileUpdateDto dto
    ) {
        log.info("Updating expert profile for userId={}", currentUser.getId());
        expertProfileService.updateExpertProfile(currentUser.getId(), dto);
        return ResponseEntity.ok("📝 Hồ sơ chuyên gia đã được cập nhật thành công.");
    }
}
