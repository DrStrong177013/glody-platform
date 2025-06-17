package com.glody.glody_platform.payment.scheduler;

import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceScheduler {

    private final InvoiceRepository invoiceRepository;

    // Chạy mỗi 1 giờ
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void markExpiredInvoices() {
        LocalDateTime now = LocalDateTime.now();
        List<Invoice> expiredInvoices = invoiceRepository.findExpiredInvoices(now);

        for (Invoice invoice : expiredInvoices) {
            invoice.setStatus(InvoiceStatus.EXPIRED);
            invoice.setUpdatedAt(now);
        }

        invoiceRepository.saveAll(expiredInvoices);

        if (!expiredInvoices.isEmpty()) {
            System.out.println("🕒 [Scheduler] Đã cập nhật " + expiredInvoices.size() + " hóa đơn hết hạn.");
        }
    }
}
