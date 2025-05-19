package com.glody.glody_platform.logging.service;

import com.glody.glody_platform.logging.dto.FeedbackDto;
import com.glody.glody_platform.logging.entity.Feedback;
import com.glody.glody_platform.logging.repository.FeedbackRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public Feedback submitFeedback(FeedbackDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setContent(dto.getContent());
        return feedbackRepository.save(feedback);
    }
}
