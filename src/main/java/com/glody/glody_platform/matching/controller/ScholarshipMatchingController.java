package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.ScholarshipMatchDto;
import com.glody.glody_platform.matching.service.ScholarshipMatchingService;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.enums.SubscriptionTier;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
@Tag(name = "Scholarship Matching", description = "Gợi ý học bổng phù hợp với hồ sơ người dùng (chỉ áp dụng với gói PREMIUM)")
public class ScholarshipMatchingController {

    private final ScholarshipMatchingService matchingService;
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @GetMapping("/matching")
    @Operation(summary = "Gợi ý học bổng từ hồ sơ người dùng")
    public ResponseEntity<?> suggest(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword
    ) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Bạn cần đăng nhập để sử dụng chức năng này.");
        }

        String email = authentication.getName();

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng.");
        }

        User user = userOpt.get();

        if (!hasTier(user.getId(), SubscriptionTier.PREMIUM)) {
            return ResponseEntity.status(403).body("Bạn cần nâng cấp gói PREMIUM để sử dụng tính năng này.");
        }

        return ResponseEntity.ok(
                matchingService.suggestScholarships(user.getId(), page, size, sortBy, sortDir, keyword)
        );
    }

    private boolean hasTier(Long userId, SubscriptionTier required) {
        List<UserSubscription> subscriptions = userSubscriptionRepository.findAllActiveByUserId(userId, LocalDate.now());

        return subscriptions.stream()
                .map(sub -> {
                    try {
                        String tier = sub.getSubscriptionPackage().getName().toUpperCase();
                        return SubscriptionTier.valueOf(tier).ordinal();
                    } catch (IllegalArgumentException e) {
                        return -1;
                    }
                })
                .anyMatch(ord -> ord >= required.ordinal());
    }

}
