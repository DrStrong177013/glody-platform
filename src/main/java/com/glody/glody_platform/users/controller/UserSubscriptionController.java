package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.dto.UserSubscriptionResponseDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý các gói đăng ký của người dùng.
 */
@RestController
@RequestMapping("/api/user-subscriptions")
@RequiredArgsConstructor
@Tag(name = "User Subscription Controller", description = "Quản lý các gói đăng ký người dùng")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    /**
     * Lấy danh sách đăng ký của một người dùng, có thể lọc theo trạng thái và phân trang.
     *
     * @param userId   ID người dùng
     * @param isActive Trạng thái đăng ký (true/false)
     * @param size     Kích thước trang
     * @param page     Trang số
     * @param sortBy   Trường sắp xếp
     * @param direction Hướng sắp xếp (asc/desc)
     * @return Danh sách đăng ký theo yêu cầu
     */
    @Operation(summary = "Lấy danh sách gói đăng ký của người dùng")
    @GetMapping
    public ResponseEntity<PageResponse<UserSubscriptionResponseDto>> getSubscriptions(
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
            Page<UserSubscriptionResponseDto> paged =
                    userSubscriptionService.searchByUserAndStatus(userId, isActive, pageable);

            PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                    paged.getNumber(),
                    paged.getSize(),
                    paged.getTotalPages(),
                    paged.getTotalElements(),
                    paged.hasNext(),
                    paged.hasPrevious()
            );

            return ResponseEntity.ok(new PageResponse<>(paged.getContent(), pageInfo));
        }

        // Trường hợp không phân trang
        List<UserSubscriptionResponseDto> list =
                userSubscriptionService.getAllByUserIdAndStatus(userId, isActive);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0, list.size(), 1, list.size(), false, false
        );

        return ResponseEntity.ok(new PageResponse<>(list, pageInfo));
    }

    /**
     * Người dùng đăng ký gói dịch vụ mới.
     *
     * @param userId ID người dùng
     * @param dto    Dữ liệu đăng ký
     * @return Đăng ký vừa được tạo
     */
    @Operation(summary = "Tạo đăng ký mới cho người dùng")
    @PostMapping
    public ResponseEntity<UserSubscriptionResponseDto> subscribe(
            @RequestParam Long userId,
            @RequestBody @Valid UserSubscriptionRequestDto dto
    ) {
        UserSubscriptionResponseDto response = userSubscriptionService.createSubscription(userId, dto);
        return ResponseEntity.ok(response);
    }

    // ❌ Xoá mềm (commented) – Nếu cần thì mở lại endpoint này.
    /*
    @Operation(summary = "Xoá mềm đăng ký của người dùng")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        userSubscriptionService.softDelete(id);
        return ResponseEntity.ok("🗑️ Subscription soft deleted successfully.");
    }
    */
}
