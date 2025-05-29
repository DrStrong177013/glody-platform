package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.dto.UserResponseDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    // ✅ Lấy 1 user theo ID
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !Boolean.TRUE.equals(u.getIsDeleted()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction
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

            PageResponse<UserResponseDto> response = new PageResponse<>(items, pageInfo);
            return ResponseEntity.ok(response);
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

        PageResponse<UserResponseDto> response = new PageResponse<>(items, pageInfo);
        return ResponseEntity.ok(response);
    }





    // ✅ Soft delete
    @DeleteMapping("/{id}")
    public String softDeleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(true);
        user.setDeletedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return "User soft deleted successfully";
    }

    // ✅ Restore
    @PutMapping("/restore/{id}")
    public String restoreUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            user.setIsDeleted(false);
            user.setDeletedAt(null);
            userRepository.save(user);
            return "User restored successfully";
        } else {
            return "User is already active";
        }
    }

    // Helper convert
    private UserResponseDto convertToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
