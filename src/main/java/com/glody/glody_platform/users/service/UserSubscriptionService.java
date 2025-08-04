package com.glody.glody_platform.users.service;

import com.glody.glody_platform.common.exception.BusinessLogicException;
import com.glody.glody_platform.users.dto.SubscriptionValidationResult;
import com.glody.glody_platform.users.dto.UserSubscriptionDto;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.dto.UserSubscriptionResponseDto;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.mapper.UserSubscriptionMapper;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;


    public Page<UserSubscriptionResponseDto> searchByUserAndStatus(Long userId, Boolean isActive, Pageable pageable) {
        if (isActive == null) {
            return userSubscriptionRepository.findByUserIdAndIsDeletedFalse(userId, pageable)
                    .map(UserSubscriptionMapper::toDto);
        }
        return userSubscriptionRepository.findByUserIdAndIsDeletedFalseAndIsActive(userId, isActive, pageable)
                .map(UserSubscriptionMapper::toDto);
    }

    public List<UserSubscriptionResponseDto> getAllByUserIdAndStatus(Long userId, Boolean isActive) {
        List<UserSubscription> list;
        if (isActive == null) {
            list = userSubscriptionRepository.findAllByUserIdAndIsDeletedFalse(userId);
        } else {
            list = userSubscriptionRepository.findAllByUserIdAndIsDeletedFalseAndIsActive(userId, isActive);
        }
        return list.stream().map(UserSubscriptionMapper::toDto).toList();
    }


    @Transactional
    public UserSubscriptionResponseDto createSubscription(Long userId, UserSubscriptionRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionPackage requestedPackage = subscriptionPackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // 🔎 Lấy tất cả các gói đang active
        List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);

        for (UserSubscription sub : activeSubs) {
            SubscriptionPackage currentPackage = sub.getSubscriptionPackage();

            // ❌ 1. Trùng gói đang dùng → chặn
            if (currentPackage.getId().equals(requestedPackage.getId())) {
                throw new BusinessLogicException("Bạn đã đăng ký gói này và đang còn hiệu lực đến: " + sub.getEndDate());
            }

            // ❌ 2. Gói mới thấp hơn → chặn
            if (requestedPackage.getPrice() < currentPackage.getPrice()) {
                throw new IllegalStateException(
                        "Không thể đăng ký gói thấp hơn (" + requestedPackage.getName() +
                                ") khi bạn đang dùng gói cao hơn (" + currentPackage.getName() + ")."
                );
            }
        }

        // ✅ Nếu hợp lệ → hết hạn gói cũ
        autoExpireSubscriptions(userId);

        // 🆕 Tạo subscription mới
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(requestedPackage.getDurationDays());

        UserSubscription sub = new UserSubscription();
        sub.setUser(user);
        sub.setSubscriptionPackage(requestedPackage);
        sub.setStartDate(start);
        sub.setEndDate(end);
        sub.setIsActive(true);
        sub.setIsDeleted(false);

        return UserSubscriptionMapper.toDto(userSubscriptionRepository.save(sub));
    }

    /**
     * Kiểm tra xem người dùng có đang sử dụng gói nào không,
     * đã hết hạn chưa, gói mới so với gói cũ (cao/thấp hơn), có thể đăng ký không?
     * @param userId user
     * @param requestedPackageId id gói muốn mua
     * @throws BusinessLogicException nếu vi phạm logic đăng ký gói
     */
    public SubscriptionValidationResult validateSubscriptionUpgrade(Long userId, Long requestedPackageId) {
        SubscriptionPackage requestedPackage = subscriptionPackageRepository.findById(requestedPackageId)
                .orElse(null);
        if (requestedPackage == null) {
            return new SubscriptionValidationResult(false, "Package not found");
        }

        // Tìm tất cả gói đang active của user (chưa expired, chưa xóa)
        List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);

        for (UserSubscription sub : activeSubs) {
            SubscriptionPackage currentPackage = sub.getSubscriptionPackage();
            if (currentPackage.getId().equals(requestedPackage.getId())) {
                return new SubscriptionValidationResult(
                        false,
                        "Bạn đã đăng ký gói này và đang còn hiệu lực đến: " + sub.getEndDate()
                );
            }
            if (requestedPackage.getPrice() < currentPackage.getPrice()) {
                return new SubscriptionValidationResult(
                        false,
                        "Không thể đăng ký gói thấp hơn (" + requestedPackage.getName() +
                                ") khi bạn đang dùng gói cao hơn (" + currentPackage.getName() + ")."
                );
            }
        }
        return new SubscriptionValidationResult(true, "Đăng ký hợp lệ");
    }


    /**
     * Đăng ký gói subscription mới cho user — chỉ gọi khi validateSubscriptionUpgrade đã pass!
     */
    @Transactional
    public UserSubscriptionResponseDto registerSubscription(Long userId, Long packageId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        expireActiveSubscriptions(userId);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(subscriptionPackage.getDurationDays());

        UserSubscription newSub = new UserSubscription();
        newSub.setUser(user);
        newSub.setSubscriptionPackage(subscriptionPackage);
        newSub.setStartDate(start);
        newSub.setEndDate(end);
        newSub.setIsActive(true);
        newSub.setIsDeleted(false);

        UserSubscription savedSub = userSubscriptionRepository.save(newSub);
        return UserSubscriptionMapper.toDto(savedSub);
    }


    /**
     * Hủy/hết hạn các subscription đang còn hiệu lực cho user.
     */
    @Transactional
    public void expireActiveSubscriptions(Long userId) {
        List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);
        LocalDate today = LocalDate.now();

        for (UserSubscription sub : activeSubs) {
            sub.setIsActive(false);
            sub.setEndDate(today); // hết hạn ngay tại thời điểm đăng ký gói mới
        }
        userSubscriptionRepository.saveAll(activeSubs);

        // Nếu user không còn gói active nào, có thể gán gói FREE ở đây nếu cần (optional)
    }




    public void softDelete(Long id) {
        UserSubscription sub = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        sub.setIsDeleted(true);
        sub.setIsActive(false);
        sub.setDeletedAt(LocalDateTime.now());

        userSubscriptionRepository.save(sub);
    }

    private void autoExpireSubscriptions(Long userId) {
        List<UserSubscription> activeSubs = userSubscriptionRepository.findAllByUserIdAndIsActiveTrue(userId);
        LocalDate today = LocalDate.now();

        for (UserSubscription sub : activeSubs) {
            if (sub.getEndDate().isBefore(today)) {
                sub.setIsActive(false);
            }
        }

        userSubscriptionRepository.saveAll(activeSubs);

        boolean hasActive = userSubscriptionRepository
                .findTopByUserIdAndIsDeletedFalseAndIsActiveTrueOrderByEndDateDesc(userId)
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
                freeSub.setIsDeleted(false);
                userSubscriptionRepository.save(freeSub);
            });
        }
    }

    private UserSubscriptionDto toDto(UserSubscription sub) {
        UserSubscriptionDto dto = new UserSubscriptionDto();
        dto.setPackageId(sub.getSubscriptionPackage().getId());
        dto.setStartDate(sub.getStartDate());
        dto.setEndDate(sub.getEndDate());
        dto.setIsActive(sub.getIsActive());
        return dto;
    }
}
