package com.glody.glody_platform.common;

import com.glody.glody_platform.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final JwtTokenUtil jwtTokenUtil;

    public Long extractUserId(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtTokenUtil.validateToken(token)) {
                return Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
            }
        }
        throw new RuntimeException("Token không hợp lệ hoặc thiếu Authorization header");
    }

}
