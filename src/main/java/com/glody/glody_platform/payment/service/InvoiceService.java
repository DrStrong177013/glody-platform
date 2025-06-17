package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceItemRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.InvoiceItem;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.InvoiceItemRepository;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SubscriptionPackageRepository packageRepository;
    private final UserRepository userRepository; // Thêm UserRepository

    @Transactional
    public InvoiceResponseDto createInvoice(CreateInvoiceRequestDto dto, Long userId) {
        SubscriptionPackage pack = packageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setCode(code);
        invoice.setPackageId(pack.getId());
        invoice.setTotalAmount(pack.getPrice());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setNote(dto.getNote());

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

        InvoiceResponseDto res = new InvoiceResponseDto();
        res.setId(invoice.getId());
        res.setCode(invoice.getCode());
        res.setPackageId(invoice.getPackageId());
        res.setPackageName(pack.getName());
        res.setTotalAmount(invoice.getTotalAmount());
        res.setStatus(invoice.getStatus().name());
        res.setCreatedAt(invoice.getCreatedAt());

        return res;
    }
}

