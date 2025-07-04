package com.glody.glody_platform.logging.controller;

import com.glody.glody_platform.logging.dto.FeedbackDto;
import com.glody.glody_platform.logging.entity.Feedback;
import com.glody.glody_platform.logging.service.FeedbackService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Hidden
@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> submit(@RequestBody FeedbackDto dto) {
        return ResponseEntity.ok(feedbackService.submitFeedback(dto));
    }
}
