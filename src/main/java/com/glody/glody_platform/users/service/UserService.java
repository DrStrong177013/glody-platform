// UserService.java
package com.glody.glody_platform.users.service;

import com.glody.glody_platform.expert.entity.ExpertProfile;
import com.glody.glody_platform.expert.repository.ExpertProfileRepository;
import com.glody.glody_platform.users.dto.UserDto;
import com.glody.glody_platform.users.entity.*;
import com.glody.glody_platform.users.repository.RoleRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
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
    private final UserProfileRepository userProfileRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final ExpertProfileRepository expertProfileRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDto userDto, boolean isExpert) {
        if (userRepository.existsByEmailAndIsDeletedFalse(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        String roleName = isExpert ? "EXPERT" : "STUDENT";
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRoles(Collections.singleton(role));
        user.setFullName(userDto.getFullName());
        User savedUser = userRepository.save(user);

        // Tạo UserProfile
        UserProfile profile = new UserProfile();
        profile.setUser(savedUser);
        profile.setFullName(userDto.getFullName());
        userProfileRepository.save(profile);

        // Nếu expert thì tạo ExpertProfile
        if (isExpert) {
            ExpertProfile expertProfile = new ExpertProfile();
            expertProfile.setUser(savedUser);
            expertProfile.setBio("");
            expertProfile.setExpertise("");
            expertProfile.setExperience("");
            expertProfile.setYearsOfExperience(0);
            expertProfileRepository.save(expertProfile);
        }

        // Gán gói FREE
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
