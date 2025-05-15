package com.glody.glody_platform.payment.service;
import com.glody.glody_platform.payment.dto.PaymentResultDto;
import com.glody.glody_platform.users.entity.*;
import com.glody.glody_platform.users.repository.*;
import com.glody.glody_platform.payment.entity.*;
import com.glody.glody_platform.payment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final UserRepository userRepository;
    private final SubscriptionPackageRepository packageRepository;
    private final UserSubscriptionRepository subscriptionRepository;

    @Transactional
    public PaymentResultDto processPayment(Long userId, Long packageId, String method) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPackage pack = packageRepository.findById(packageId).orElseThrow(() -> new RuntimeException("Package not found"));

        // 1. Giao dịch thanh toán
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(pack.getPrice());
        payment.setMethod(method);
        payment.setStatus("SUCCESS");
        payment.setTransactionCode(UUID.randomUUID().toString());
        paymentRepository.save(payment);

        // 2. Hóa đơn
        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setTotalAmount(pack.getPrice());
        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setSubscriptionPackage(pack);
        item.setQuantity(1);
        item.setUnitPrice(pack.getPrice());
        invoiceItemRepository.save(item);

        invoice.setItems(Collections.singletonList(item));

        // 3. Gán gói cho user
        UserSubscription subscription = new UserSubscription();
        subscription.setUser(user);
        subscription.setSubscriptionPackage(pack);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(pack.getDurationDays()));
        subscription.setIsActive(true);
        subscriptionRepository.save(subscription);

        // ✅ Trả kết quả
        PaymentResultDto result = new PaymentResultDto();
        result.setTransactionCode(payment.getTransactionCode());
        result.setAmount(payment.getAmount());
        result.setMethod(payment.getMethod());
        result.setStatus(payment.getStatus());
        result.setInvoiceId(invoice.getId());
        return result;
    }
    public List<Payment> getPaymentHistory(Long userId) {
        return paymentRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Invoice> getInvoiceHistory(Long userId) {
        return invoiceRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }


}