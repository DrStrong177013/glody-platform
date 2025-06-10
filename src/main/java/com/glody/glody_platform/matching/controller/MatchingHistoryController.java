package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.MatchingHistoryDto;
import com.glody.glody_platform.matching.service.MatchingHistoryService;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/matching-history")
@RequiredArgsConstructor
@Tag(name = "Matching History Controller", description = "API lấy lịch sử matching của người dùng")
public class MatchingHistoryController {

    private final MatchingHistoryService service;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Lấy lịch sử matching (program, scholarship) của người dùng (có phân trang, lọc, sắp xếp)")
    public ResponseEntity<?> getMatchingHistory(
            Authentication authentication,
            @RequestParam String matchType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Bạn cần đăng nhập để sử dụng chức năng này.");
        }

        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng.");
        }

        Long userId = userOpt.get().getId();

        return ResponseEntity.ok(service.getMatchingHistory(userId, matchType, page, size));
    }
}
