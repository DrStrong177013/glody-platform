package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPackageRequestDto {

    @NotBlank(message = "Name không được rỗng")
    @Schema(description = "Tên Gói", example = "P_R_O")
    private String name;          // FREE, BASIC, PREMIUM

    @Schema(description = "Mô tả của gói", example = "Các tính năng thử nghiệm với giá siêu khuyến mãi")
    private String description;

    @NotNull(message = "Price bắt buộc phải có")
    @PositiveOrZero(message = "Price phải >= 0")
    @Schema(description = "Giá của gói (tính theo VND)", example = "2000")
    private Double price;

    @NotNull(message = "DurationDays bắt buộc phải có")
    @Positive(message = "DurationDays phải > 0")
    @Schema(description = "Thời hạn của gói", example = "3456")
    private Integer durationDays;

    @Schema(description = "Các tính năng của gói", example = "Thử nghiệm tính năng thanh toán")
    private String features;
}
