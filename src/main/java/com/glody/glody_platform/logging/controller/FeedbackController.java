package com.glody.glody_platform.logging.controller;

import com.glody.glody_platform.logging.dto.FeedbackDto;
import com.glody.glody_platform.logging.entity.Feedback;
import com.glody.glody_platform.logging.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "API quản lý feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "Gửi feedback mới", description = "Tạo feedback mới từ người dùng")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedback đã được tạo thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
        @ApiResponse(responseCode = "500", description = "Lỗi server")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Feedback> submit(@Valid @RequestBody FeedbackDto dto) {
        return ResponseEntity.ok(feedbackService.submitFeedback(dto));
    }
}