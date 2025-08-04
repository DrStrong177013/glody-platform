package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.payment.dto.*;
import com.glody.glody_platform.payment.entity.*;
import com.glody.glody_platform.payment.enums.*;
import com.glody.glody_platform.payment.repository.*;
import com.glody.glody_platform.users.entity.*;
import com.glody.glody_platform.users.repository.*;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Invoice invoice = invoiceRepository.findByCode(orderCode.toString())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if ("00".equals(statusCode)) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.FAILED);
        }
        invoiceRepository.save(invoice);
    }

    // Getby By Id, Get All
    // User chỉ xem hóa đơn của mình
    public PageResponse<UserInvoiceResponseDto> getAllByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Invoice> pageObj = invoiceRepository.findAllByUserId(userId, pageable);
        List<UserInvoiceResponseDto> items = pageObj.stream()
                .map(this::toUserDto)
                .toList();
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                pageObj.getNumber(),
                pageObj.getSize(),
                pageObj.getTotalPages(),
                pageObj.getTotalElements(),
                pageObj.hasNext(),
                pageObj.hasPrevious()
        );
        return new PageResponse<>(items, pageInfo);
    }

    // Admin xem tất cả
    public PageResponse<AdminInvoiceResponseDto> getAllAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Invoice> pageObj = invoiceRepository.findAll(pageable);
        List<AdminInvoiceResponseDto> items = pageObj.stream()
                .map(this::toAdminDto)
                .toList();
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                pageObj.getNumber(),
                pageObj.getSize(),
                pageObj.getTotalPages(),
                pageObj.getTotalElements(),
                pageObj.hasNext(),
                pageObj.hasPrevious()
        );
        return new PageResponse<>(items, pageInfo);
    }

    public UserInvoiceResponseDto getByIdForUser(Long id, Long userId) {
        return invoiceRepository.findByIdAndUserId(id, userId)
                .map(this::toUserDto)
                .orElse(null);
    }

    public AdminInvoiceResponseDto getByIdAdmin(Long id) {
        return invoiceRepository.findById(id)
                .map(this::toAdminDto)
                .orElse(null);
    }

    public PageResponse<AdminInvoiceResponseDto> getAllByUserForAdmin(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Invoice> pageObj = invoiceRepository.findAllByUserId(userId, pageable);
        List<AdminInvoiceResponseDto> items = pageObj.stream()
                .map(this::toAdminDto)
                .toList();
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                pageObj.getNumber(),
                pageObj.getSize(),
                pageObj.getTotalPages(),
                pageObj.getTotalElements(),
                pageObj.hasNext(),
                pageObj.hasPrevious()
        );
        return new PageResponse<>(items, pageInfo);
    }

    // Mapping functions
    private UserInvoiceResponseDto toUserDto(Invoice inv) {
        UserInvoiceResponseDto dto = new UserInvoiceResponseDto();
        dto.setId(inv.getId());
        dto.setCode(inv.getCode());
        dto.setTotalAmount(inv.getTotalAmount().intValue());
        dto.setNote(inv.getNote());
        dto.setStatus(inv.getStatus().name());
        dto.setCreatedAt(inv.getCreatedAt());
        dto.setExpiredAt(inv.getExpiredAt());
        dto.setPackageId(inv.getPackageId());
        return dto;
    }

    private AdminInvoiceResponseDto toAdminDto(Invoice inv) {
        AdminInvoiceResponseDto dto = new AdminInvoiceResponseDto();
        dto.setId(inv.getId());
        dto.setCode(inv.getCode());
        dto.setTotalAmount(inv.getTotalAmount().intValue());
        dto.setNote(inv.getNote());
        dto.setStatus(inv.getStatus().name());
        dto.setCreatedAt(inv.getCreatedAt());
        dto.setExpiredAt(inv.getExpiredAt());
        dto.setPackageId(inv.getPackageId());
        dto.setUserId(inv.getUser() != null ? inv.getUser().getId() : null);
        return dto;
    }
}
