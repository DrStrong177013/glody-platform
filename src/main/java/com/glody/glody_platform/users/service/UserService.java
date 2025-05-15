// UserService.java
package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.entity.Role;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.repository.RoleRepository;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDto userDto) {
        if (userRepository.existsByEmailAndIsDeletedFalse(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role userRole = roleRepository.findByRoleName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setAvatarUrl(userDto.getAvatarUrl());
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userRepository.save(user);

        SubscriptionPackage freePackage = subscriptionPackageRepository.findByName("FREE")
                .orElseThrow(() -> new RuntimeException("Default FREE package not found"));

        UserSubscription subscription = new UserSubscription();
        subscription.setUser(savedUser);
        subscription.setSubscriptionPackage(freePackage);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(freePackage.getDurationDays()));
        subscription.setIsActive(true);

        userSubscriptionRepository.save(subscription);

        return savedUser;
    }
}
