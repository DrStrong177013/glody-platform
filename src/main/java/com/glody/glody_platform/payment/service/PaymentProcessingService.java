package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final UserSubscriptionService userSubscriptionService;
    private final UserRepository userRepository;

    @Transactional
    public void processVnPayReturn(Map<String, String> params) {
        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        String status = params.get("vnp_TransactionStatus");

        Invoice invoice = invoiceRepository.findByCode(txnRef)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (!"00".equals(responseCode) || !"00".equals(status)) {
            invoice.setStatus(InvoiceStatus.FAILED);
            invoiceRepository.save(invoice);
            return;
        }


        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        invoiceRepository.save(invoice);

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setTransactionId(params.get("vnp_TransactionNo"));
        payment.setBankCode(params.get("vnp_BankCode"));
        payment.setCardType(params.get("vnp_CardType"));
        payment.setPaidAt(LocalDateTime.now());
        payment.setResponseCode(responseCode);
        payment.setProvider("VNPAY");
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        // Apply subscription
        UserSubscriptionRequestDto subDto = new UserSubscriptionRequestDto();
        subDto.setPackageId(invoice.getPackageId());
        userSubscriptionService.createSubscription(invoice.getUser().getId(), subDto);
    }
}
