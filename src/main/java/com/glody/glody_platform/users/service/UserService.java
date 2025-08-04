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
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private MailService mailService;

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

    public void sendForgotPasswordEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            user.setResetPasswordExpiry(LocalDateTime.now().plusHours(1));
            userRepository.save(user);

            String resetLink = "https://glodyai.com/reset-password?token=" + token;
            String html = "<p>Click vào đây để đặt lại mật khẩu: <a href='" + resetLink + "'>Reset Password</a></p>";
            try {
                mailService.sendHtml(email, "GLODY - Reset password", html);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException("Gửi email thất bại", e);
            }
        }
    }


    // Đổi mật khẩu với token
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByResetPasswordToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getResetPasswordExpiry() != null && user.getResetPasswordExpiry().isAfter(LocalDateTime.now())) {
                // Đổi password
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                user.setResetPasswordToken(null);
                user.setResetPasswordExpiry(null);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }



}
