package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.*;
        import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.*;
        import vn.payos.type.ItemData;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final InvoiceService invoiceService;
    private final PayosService payosService;
    private final PaymentRepository paymentRepository;
    private final PayOS payos;
    private final UserSubscriptionService subService;

    @Transactional
    public InvoiceResponseDto createInvoiceAndPayment(CreateInvoiceRequestDto dto, Long userId) {
        subService.validateSubscriptionUpgrade(userId, dto.getPackageId());
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

    @Transactional
    public void updatePaymentByOrderCode(Long orderCode, PaymentStatus newStatus, Webhook webhookRequest) {
        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(orderCode.toString());
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus(newStatus);
            payment.setResponseSignature(webhookRequest.getSignature());
            payment.setBankCode(webhookRequest.getData().getCounterAccountBankName());
            payment.setCardType("on DEV");
            payment.setPaidAt(LocalDateTime.now());
            payment.setResponseCode(webhookRequest.getData().getCode());
            payment.setPaymentLinkId(webhookRequest.getData().getPaymentLinkId());

            paymentRepository.save(payment);
            System.out.println("UpdatePayment by Webhook " + payment);
        } else {
            System.out.println("Payment not found for orderCode: " + orderCode);
        }
    }

    @Transactional
    public boolean handlePayosWebhook(Webhook webhookRequest) {
        System.out.println("PayOS Webhook received: " + webhookRequest);

        if (!payosService.validateWebhook(webhookRequest)) {
            System.out.println("PayOS Webhook signature invalid: " + webhookRequest);
            return false;
        }
        WebhookData data = webhookRequest.getData();

        // Cập nhật trạng thái hóa đơn
        invoiceService.updateInvoiceStatus(data.getOrderCode(), data.getCode());

        PaymentStatus paymentStatus = "00".equals(data.getCode()) ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        this.updatePaymentByOrderCode(data.getOrderCode(), paymentStatus, webhookRequest);

        if (paymentStatus == PaymentStatus.SUCCESS) {

            Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(data.getOrderCode().toString());
            if (paymentOpt.isPresent()) {
                Payment payment = paymentOpt.get();
                Invoice invoice = payment.getInvoice();
                if (invoice != null) {
                    Long userId = invoice.getUser().getId();
                    Long packageId = invoice.getPackageId();
                    try {
                        subService.validateSubscriptionUpgrade(userId, packageId);
                        subService.registerSubscription(userId, packageId);
                    } catch (Exception e) {
                        System.out.println("Không thể đăng ký gói sub mới: " + e.getMessage());
                    }
                } else {
                    System.out.println("Không tìm thấy invoice cho payment orderCode: " + data.getOrderCode());
                }
            } else {
                System.out.println("Payment not found for orderCode: " + data.getOrderCode());
            }
        }

        return true;
    }

}
