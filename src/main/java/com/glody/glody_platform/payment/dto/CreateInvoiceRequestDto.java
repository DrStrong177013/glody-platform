package com.glody.glody_platform.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateInvoiceRequestDto {
    @Schema(description = "Gói đăng ký muốn mua", example = "2")
    @NotBlank(message = "không được để trống gói đăng ký")
    @Min(2)
    private Long   packageId;
    @Schema(description = "Đường linh tự trả về sau khi thanh toán xong", example = "https://glodyai.com/about")
    private String returnUrl;
    @Schema(description = "Đường linh tự trả về khi thanh toán thất bại", example = "https://glodyai.com/about")
    private String cancelUrl;
}