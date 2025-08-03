package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.PayosNotification;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository      paymentRepository;
    private final InvoiceRepository      invoiceRepository;
    private final PayosService           payosService;
    private final UserSubscriptionService subService;

    /**
     * Xử lý webhook từ PayOS.
     */
    @Transactional
    public void handlePayosCallback(PayosNotification notif) {
        // 1) Verify signature
        if (!payosService.verifyNotification(notif)) {
            throw new IllegalArgumentException("Invalid PayOS signature");
        }

        // 2) Tìm payment theo orderCode = invoice.id
        Payment pay = paymentRepository
                .findByInvoiceId(notif.getOrderCode())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        // 3) Cập nhật payment & invoice
        if ("SUCCESS".equalsIgnoreCase(notif.getStatus())) {
            pay.setStatus(PaymentStatus.SUCCESS);
            pay.setPaidAt(LocalDateTime.now());
            paymentRepository.save(pay);

            Invoice inv = pay.getInvoice();
            inv.setStatus(InvoiceStatus.PAID);
            inv.setPaidAt(LocalDateTime.now());
            invoiceRepository.save(inv);
            UserSubscriptionRequestDto subReq = new UserSubscriptionRequestDto();

            // 4) Kích hoạt subscription cho user
            subReq.setPackageId(inv.getPackageId());
            subService.createSubscription(
                    inv.getUser().getId(),
                    subReq
            );
        } else {
            pay.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(pay);
        }
    }
}
