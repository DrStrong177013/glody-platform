package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.CreatePaymentResponse;
import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.InvoiceItem;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository             invoiceRepository;
    private final PaymentRepository             paymentRepository;
    private final SubscriptionPackageRepository packageRepository;
    private final UserRepository                userRepository;
    private final PayosService                  payosService;
    private final UserSubscriptionService       subService;

    @Transactional
    public InvoiceResponseDto createInvoice(CreateInvoiceRequestDto dto, Long userId) {
        // 1) Lấy gói và user
        SubscriptionPackage pack = packageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Tạo mã hóa đơn
        String txnRef = String.valueOf(System.currentTimeMillis());

        // 3) Khởi tạo Invoice và item
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

        // 4) Lưu Invoice (cascade lưu item)
        invoiceRepository.save(invoice);

        // 5) --- SINH LINK THANH TOÁN PAYOS ---
        CreatePaymentResponse payResp = payosService.createLink(
                invoice.getId(),                     // orderCode = invoice.id
                pack.getPrice().longValue(),         // amount phải là Long
                dto.getReturnUrl(),                  // returnUrl từ DTO
                dto.getCancelUrl()                   // cancelUrl từ DTO
        );

        // 6) Lưu Payment record với checkoutUrl
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setUser(user);
        payment.setProvider("PayOS");
        payment.setStatus(PaymentStatus.PENDING);          // hoặc LINK_CREATED nếu bạn muốn
        payment.setCheckoutUrl(payResp.getData().getCheckoutUrl());
        payment.setTransactionId(invoice.getCode());
        paymentRepository.save(payment);

        // 7) Đóng gói response
        InvoiceResponseDto res = new InvoiceResponseDto();
        res.setId(invoice.getId());
        res.setCode(invoice.getCode());
        res.setPackageId(invoice.getPackageId());
        res.setNote(invoice.getNote());
        res.setTotalAmount(invoice.getTotalAmount());
        res.setStatus(invoice.getStatus().name());
        res.setCreatedAt(invoice.getCreatedAt());
        res.setExpiredAt(invoice.getExpiredAt());
        res.setCheckoutUrl(payResp.getData().getCheckoutUrl());  // trả về link

        return res;
    }
}
