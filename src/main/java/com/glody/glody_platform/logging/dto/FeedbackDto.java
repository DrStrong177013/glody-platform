package com.glody.glody_platform.logging.dto;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Getter
@Setter
public class FeedbackDto {
    @NotNull(message = "SenderId không được để trống")
    private Long senderId;
    
    @NotNull(message = "ReceiverId không được để trống")
    private Long receiverId;
    
    @NotBlank(message = "Nội dung feedback không được để trống")
    @Size(max = 1000, message = "Nội dung feedback không được vượt quá 1000 ký tự")
    private String feedbackText;
    
    @NotNull(message = "Rating không được để trống")
    @Min(value = 1, message = "Rating phải từ 1 đến 5")
    @Max(value = 5, message = "Rating phải từ 1 đến 5")
    private Integer rating;
}