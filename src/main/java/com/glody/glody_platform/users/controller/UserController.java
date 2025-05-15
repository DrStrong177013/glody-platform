package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !Boolean.TRUE.equals(user.getIsDeleted()))
                .toList();
    }

    @PostMapping("/register")
    public User register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @DeleteMapping("/{id}")
    public String softDeleteUser(@PathVariable String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User soft deleted successfully";
    }
    @PutMapping("/restore/{id}")
    public String restoreUser(@PathVariable String id) {
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
}