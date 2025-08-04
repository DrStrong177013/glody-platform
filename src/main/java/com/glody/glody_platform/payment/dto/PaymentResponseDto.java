package com.glody.glody_platform.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long id;
    private String transactionId;       // Mã giao dịch (orderCode)
    private String provider;            // Tên cổng thanh toán (PayOS, v.v.)
    private String status;              // Trạng thái: PENDING, SUCCESS, FAILED
    private LocalDateTime paidAt;       // Thời gian thanh toán (nếu có)
    private String bankCode;            // Mã ngân hàng (nếu cần)
    private Long invoiceId;             // Id hóa đơn liên kết (nếu muốn show)
    private String payment_link_id;




}
