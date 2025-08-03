package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.*;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final InvoiceService invoiceService;
    private final PayosService payosService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public InvoiceResponseDto createInvoiceAndPayment(CreateInvoiceRequestDto dto, Long userId) {
        Invoice invoice = invoiceService.createInvoice(dto, userId);

        // Gọi sang PayosService để lấy link thanh toán
        CreatePaymentResponse payResp = payosService.createLink(
                invoice.getId(),
                invoice.getTotalAmount().longValue(),
                dto.getReturnUrl(),
                dto.getCancelUrl()
        );
        String checkoutUrl = payResp.getData().getCheckoutUrl();

        // Lưu record payment
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setUser(invoice.getUser());
        payment.setProvider("PayOS");
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCheckoutUrl(checkoutUrl);
        payment.setTransactionId(invoice.getCode());
        paymentRepository.save(payment);

        // Đóng gói response
        InvoiceResponseDto res = new InvoiceResponseDto();
        res.setId(invoice.getId());
        res.setCode(invoice.getCode());
        res.setPackageId(invoice.getPackageId());
        res.setNote(invoice.getNote());
        res.setTotalAmount(invoice.getTotalAmount());
        res.setStatus(invoice.getStatus().name());
        res.setCreatedAt(invoice.getCreatedAt());
        res.setExpiredAt(invoice.getExpiredAt());
        res.setCheckoutUrl(checkoutUrl);

        return res;
    }

    public boolean handlePayosWebhook(PayosWebhookRequest webhookRequest) {
        if (!payosService.validateSignature(webhookRequest)) {
            return false;
        }
        PayosNotificationData data = webhookRequest.getData();
        invoiceService.updateInvoiceStatus(data.getOrderCode(), data.getCode());
        return true;
    }
}
