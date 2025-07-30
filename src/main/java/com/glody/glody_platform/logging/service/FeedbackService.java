package com.glody.glody_platform.logging.service;

import com.glody.glody_platform.common.exception.ResourceNotFoundException;
import com.glody.glody_platform.logging.dto.FeedbackDto;
import com.glody.glody_platform.logging.entity.Feedback;
import com.glody.glody_platform.logging.repository.FeedbackRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    /**
     * Tạo feedback mới từ người dùng
     * @param dto Thông tin feedback
     * @return Feedback đã được lưu
     * @throws IllegalArgumentException nếu dữ liệu không hợp lệ
     * @throws ResourceNotFoundException nếu không tìm thấy người dùng
     */
    @Transactional
    public Feedback submitFeedback(FeedbackDto dto) {
        log.info("Bắt đầu tạo feedback từ user {} đến user {}", dto.getSenderId(), dto.getReceiverId());
        
        validateFeedbackDto(dto);
        
        User sender = userRepository.findById(dto.getSenderId())
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người gửi: " + dto.getSenderId()));
        
        User receiver = userRepository.findById(dto.getReceiverId())
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người nhận: " + dto.getReceiverId()));

        if (!sender.getStatus() || !receiver.getStatus()) {
            throw new IllegalStateException("Người dùng không trong trạng thái hoạt động");
        }

        Feedback feedback = new Feedback();
        feedback.setSender(sender);
        feedback.setReceiver(receiver);
        feedback.setFeedbackText(dto.getFeedbackText());
        feedback.setRating(dto.getRating());

        log.info("Lưu feedback mới");
        return feedbackRepository.save(feedback);
    }

    private void validateFeedbackDto(FeedbackDto dto) {
        if (dto.getSenderId() == null || dto.getReceiverId() == null) {
            throw new IllegalArgumentException("SenderId và ReceiverId không được để trống");
        }
        
        if (dto.getSenderId().equals(dto.getReceiverId())) {
            throw new IllegalArgumentException("Không thể gửi feedback cho chính mình");
        }
        
        if (dto.getRating() == null || dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating phải có giá trị từ 1 đến 5");
        }

        if (dto.getFeedbackText() == null || dto.getFeedbackText().trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung feedback không được để trống");
        }

        if (dto.getFeedbackText().length() > 1000) {
            throw new IllegalArgumentException("Nội dung feedback quá dài");
        }
    }
}