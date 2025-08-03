package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.*;
import com.glody.glody_platform.payment.entity.*;
import com.glody.glody_platform.payment.enums.*;
import com.glody.glody_platform.payment.repository.*;
import com.glody.glody_platform.users.entity.*;
import com.glody.glody_platform.users.repository.*;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionPackageRepository packageRepository;
    private final UserRepository userRepository;
    private final UserSubscriptionService subService;

    @Transactional
    public Invoice createInvoice(CreateInvoiceRequestDto dto, Long userId) {
        SubscriptionPackage pack = packageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String txnRef = String.valueOf(System.currentTimeMillis());

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setCode(txnRef);
        invoice.setPackageId(pack.getId());
        invoice.setTotalAmount(pack.getPrice());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setNote("Đăng ký gói " + pack.getName());
        invoice.setExpiredAt(LocalDateTime.now().plusMinutes(15));

        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setItemName(pack.getName());
        item.setPrice(pack.getPrice());
        item.setQuantity(1);
        item.setDescription(pack.getDescription());
        item.setReferenceType("SUBSCRIPTION_PACKAGE");
        item.setReferenceId(pack.getId());
        invoice.setItems(List.of(item));

        invoiceRepository.save(invoice);
        return invoice;
    }

    @Transactional
    public void updateInvoiceStatus(Long orderCode, String statusCode) {
        Invoice invoice = invoiceRepository.findById(orderCode)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if ("00".equals(statusCode)) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.FAILED);
        }
        invoiceRepository.save(invoice);
    }
}
