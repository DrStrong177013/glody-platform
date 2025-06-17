package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.payment.utils.HMACUtil;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentViewController {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final UserSubscriptionService userSubscriptionService;

    @GetMapping("/payment-return")
    public String handleReturnUrl(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            vnpParams.put(entry.getKey(), URLDecoder.decode(entry.getValue()[0], StandardCharsets.UTF_8));
        }

        String receivedHash = vnpParams.get("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        List<String> sortedKeys = new ArrayList<>(vnpParams.keySet());
        Collections.sort(sortedKeys);
        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(vnpParams.get(key)).append("&");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);

        String calculatedHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, sb.toString());
        boolean isValid = calculatedHash.equals(receivedHash);
        boolean isSuccess = "00".equals(vnpParams.get("vnp_ResponseCode"));

        String txnRef = vnpParams.get("vnp_TxnRef");
        model.addAttribute("txnRef", txnRef);

        if (!isValid) {
            model.addAttribute("statusClass", "fail");
            model.addAttribute("message", "⚠️ Chữ ký không hợp lệ!");
            return "payment-result";
        }

        if (!isSuccess) {
            model.addAttribute("statusClass", "fail");
            model.addAttribute("message", "❌ Giao dịch không thành công.");
            return "payment-result";
        }

        Optional<Invoice> optionalInvoice = invoiceRepository.findByCode(txnRef);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();

            invoice.setStatus(InvoiceStatus.PAID);
            invoice.setUpdatedAt(LocalDateTime.now());
            invoiceRepository.save(invoice);

            Payment payment = new Payment();
            payment.setInvoice(invoice);
            payment.setTransactionId(vnpParams.get("vnp_TransactionNo"));
            payment.setBankCode(vnpParams.get("vnp_BankCode"));
            payment.setCardType(vnpParams.get("vnp_CardType"));
            payment.setPaidAt(LocalDateTime.now());
            payment.setProvider("VNPAY");
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setResponseCode(vnpParams.get("vnp_ResponseCode"));
            payment.setRawResponse(buildRawResponseUrl(vnpParams));
            paymentRepository.save(payment);

            // ✅ Apply subscription gói đã mua
            UserSubscriptionRequestDto subDto = new UserSubscriptionRequestDto();
            subDto.setPackageId(invoice.getPackageId());
            userSubscriptionService.createSubscription(invoice.getUser().getId(), subDto);

            model.addAttribute("statusClass", "success");
            model.addAttribute("message", "🎉 Giao dịch thành công và gói đã được kích hoạt!");
        } else {
            model.addAttribute("statusClass", "fail");
            model.addAttribute("message", "⚠️ Không tìm thấy hóa đơn!");
        }

        return "payment-result";
    }

    private String buildRawResponseUrl(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("?");
        params.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
        if (sb.length() > 1) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
