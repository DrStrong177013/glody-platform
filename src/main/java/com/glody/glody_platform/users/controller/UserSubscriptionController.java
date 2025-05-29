package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserSubscriptionDto;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.dto.UserSubscriptionResponseDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import com.glody.glody_platform.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    public ResponseEntity<?> getSubscriptions(
            @RequestParam Long userId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (size != null) {
            Pageable pageable = PageRequest.of(page != null ? page : 0, size, sort);
            Page<UserSubscriptionResponseDto> paged = userSubscriptionService.searchByUserAndStatus(userId, isActive, pageable);

            return ResponseEntity.ok(new PageResponse<>(paged.getContent(),
                    new PageResponse.PageInfo(
                            paged.getNumber(),
                            paged.getSize(),
                            paged.getTotalPages(),
                            paged.getTotalElements(),
                            paged.hasNext(),
                            paged.hasPrevious()
                    )));
        }

        List<UserSubscriptionResponseDto> list = userSubscriptionService.getAllByUserIdAndStatus(userId, isActive);
        return ResponseEntity.ok(new PageResponse<>(list,
                new PageResponse.PageInfo(0, list.size(), 1, list.size(), false, false)));
    }


    @PostMapping
    public ResponseEntity<UserSubscriptionResponseDto> subscribe(
            @RequestParam Long userId,
            @RequestBody @Valid UserSubscriptionRequestDto dto
    ) {
        return ResponseEntity.ok(userSubscriptionService.createSubscription(userId, dto));
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> softDelete(@PathVariable Long id) {
//        userSubscriptionService.softDelete(id);
//        return ResponseEntity.ok("Subscription soft deleted successfully");
//    }

}
