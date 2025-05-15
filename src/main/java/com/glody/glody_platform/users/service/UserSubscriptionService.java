package com.glody.glody_platform.users.service;

import com.glody.glody_platform.users.dto.UserSubscriptionDto;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    public UserSubscriptionDto getActiveSubscription(Long userId) {
        autoExpireSubscriptions(userId);
        UserSubscription sub = userSubscriptionRepository.findTopByUserIdAndIsActiveTrueOrderByEndDateDesc(userId)
                .orElseThrow(() -> new RuntimeException("No active subscription"));

        UserSubscriptionDto dto = new UserSubscriptionDto();
        dto.setPackageId(sub.getSubscriptionPackage().getId());
        dto.setStartDate(sub.getStartDate());
        dto.setEndDate(sub.getEndDate());
        dto.setIsActive(sub.getIsActive());
        return dto;
    }

    @Transactional
    public void createSubscription(Long userId, UserSubscriptionDto dto) {
        User user = userRepository.findById(userId).orElseThrow();
        SubscriptionPackage pack = subscriptionPackageRepository.findById(dto.getPackageId()).orElseThrow();

        autoExpireSubscriptions(userId);

        UserSubscription sub = new UserSubscription();
        sub.setUser(user);
        sub.setSubscriptionPackage(pack);
        sub.setStartDate(dto.getStartDate());
        sub.setEndDate(dto.getEndDate());
        sub.setIsActive(true);
        userSubscriptionRepository.save(sub);
    }

//    private void autoExpireSubscriptions(Long userId) {
//        List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);
//        LocalDate today = LocalDate.now();
//        for (UserSubscription sub : activeSubs) {
//            if (sub.getEndDate().isBefore(today)) {
//                sub.setIsActive(false);
//            }
//
//        }
//        userSubscriptionRepository.saveAll(activeSubs);
//    }
private void autoExpireSubscriptions(Long userId) {
    List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);
    LocalDate today = LocalDate.now();

    for (UserSubscription sub : activeSubs) {
        if (sub.getEndDate().isBefore(today)) {
            sub.setIsActive(false);
        }
    }
    userSubscriptionRepository.saveAll(activeSubs);

    // Nếu không còn gói nào active → gán gói FREE mặc định
    boolean hasActive = userSubscriptionRepository
            .findTopByUserIdAndIsActiveTrueOrderByEndDateDesc(userId)
            .isPresent();

    if (!hasActive) {
        subscriptionPackageRepository.findByName("FREE").ifPresent(free -> {
            User user = userRepository.findById(userId).orElseThrow();
            UserSubscription freeSub = new UserSubscription();
            freeSub.setUser(user);
            freeSub.setSubscriptionPackage(free);
            freeSub.setStartDate(today);
            freeSub.setEndDate(today.plusDays(free.getDurationDays()));
            freeSub.setIsActive(true);
            userSubscriptionRepository.save(freeSub);
        });
    }
}


}