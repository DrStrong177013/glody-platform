package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.*;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.*;
import vn.payos.type.ItemData;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final InvoiceService invoiceService;
    private final PayosService payosService;
    private final PaymentRepository paymentRepository;
    private final PayOS payos;

    @Transactional
    public InvoiceResponseDto createInvoiceAndPayment(CreateInvoiceRequestDto dto, Long userId) {
        Invoice invoice = invoiceService.createInvoice(dto, userId);
        ItemData itemData = ItemData.builder()
                .name(invoice.getNote())
                .quantity(1)
                .price(invoice.getTotalAmount().intValue())
                .build();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(Long.parseLong(invoice.getCode()))
                .amount(invoice.getTotalAmount().intValue())
                .description(invoice.getNote())
                .returnUrl(dto.getReturnUrl())
                .cancelUrl(dto.getCancelUrl())
                .item(itemData)
                .build();

        CheckoutResponseData response;
        try {
            response = payos.createPaymentLink(paymentData);
        } catch (Exception ex) {
            throw new RuntimeException("Gọi PayOS thất bại: " + ex.getMessage(), ex);
        }
        if (response == null || response.getCheckoutUrl() == null) {
            throw new RuntimeException("Không tạo được link thanh toán, vui lòng thử lại!");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setUser(invoice.getUser());
        payment.setProvider("PayOS");
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCheckoutUrl(response.getCheckoutUrl());
        payment.setTransactionId(invoice.getCode());
        paymentRepository.save(payment);

        InvoiceResponseDto res = new InvoiceResponseDto();
        res.setId(invoice.getId());
        res.setCode(invoice.getCode());
        res.setPackageId(invoice.getPackageId());
        res.setNote(invoice.getNote());
        res.setTotalAmount(invoice.getTotalAmount());
        res.setStatus(invoice.getStatus().name());
        res.setCreatedAt(invoice.getCreatedAt());
        res.setExpiredAt(invoice.getExpiredAt());
        res.setCheckoutUrl(response.getCheckoutUrl());

        return res;
    }


    public boolean handlePayosWebhook(Webhook webhookRequest) {
        // Log payload nhận về (nên log ở mức info/debug, không log signature ra file log production)
        System.out.println("PayOS Webhook received: " + webhookRequest);

        if (!payosService.validateWebhook(webhookRequest)) {
            // Log cảnh báo sai signature
            System.out.println("PayOS Webhook signature invalid: " + webhookRequest);
            return false;
        }
        WebhookData data = webhookRequest.getData();

        // Có thể kiểm tra trạng thái hóa đơn hiện tại, tránh update lặp nếu đã xử lý
        invoiceService.updateInvoiceStatus(data.getOrderCode(), data.getCode());
        return true;
    }



}
