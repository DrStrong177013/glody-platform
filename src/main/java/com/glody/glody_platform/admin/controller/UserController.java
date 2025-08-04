package com.glody.glody_platform.admin.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.dto.UserResponseDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller quản lý tài khoản người dùng.
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin User Controller", description = "Admin Quản lý người dùng")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Lấy thông tin người dùng theo ID.
     */
    @Operation(summary = "Lấy thông tin người dùng theo ID (Admin)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !Boolean.TRUE.equals(u.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(convertToDto(user));
    }

    /**
     * Lấy danh sách người dùng (có phân trang hoặc toàn bộ).
     */
    @Operation(summary = "Lấy danh sách người dùng (Admin)")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponseDto>> getUsers(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (size != null) {
            int pageNumber = (page != null) ? page : 0;
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            Page<User> usersPage = userRepository.findAllByIsDeletedFalse(pageable);

            List<UserResponseDto> items = usersPage.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                    usersPage.getNumber(),
                    usersPage.getSize(),
                    usersPage.getTotalPages(),
                    usersPage.getTotalElements(),
                    usersPage.hasNext(),
                    usersPage.hasPrevious()
            );

            return ResponseEntity.ok(new PageResponse<>(items, pageInfo));
        }

        // Trường hợp không phân trang
        List<UserResponseDto> items = userRepository.findAllByIsDeletedFalse(sort).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0,
                items.size(),
                1,
                items.size(),
                false,
                false
        );

        return ResponseEntity.ok(new PageResponse<>(items, pageInfo));
    }

    /**
     * Xóa mềm người dùng theo ID.
     */
    @Operation(summary = "Xoá mềm người dùng theo ID (Admin)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok("🗑️ User đã bị xoá mềm.");
    }

    /**
     * Khôi phục người dùng đã bị xoá mềm.
     */
    @Operation(summary = "Khôi phục người dùng đã xoá mềm (Admin)")
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            user.setIsDeleted(false);
            user.setDeletedAt(null);
            userRepository.save(user);
            return ResponseEntity.ok("✅ User đã được khôi phục.");
        }

        return ResponseEntity.ok("User đang hoạt động, không cần khôi phục.");
    }

    /**
     * Chuyển đổi entity User -> DTO để trả về client.
     */
    private UserResponseDto convertToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
