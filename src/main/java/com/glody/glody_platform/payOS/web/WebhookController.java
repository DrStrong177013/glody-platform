package com.glody.glody_platform.payOS.web;

import com.glody.glody_platform.payOS.dto.PayosNotification;
import com.glody.glody_platform.payOS.service.PayosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook/payos")
public class WebhookController {
    private final PayosService service;

    public WebhookController(PayosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody PayosNotification notif) {
        // 1) verify signature tương tự createLink
        // 2) cập nhật DB
        return ResponseEntity.ok("OK");
    }
}
