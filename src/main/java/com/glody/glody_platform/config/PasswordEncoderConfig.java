package com.glody.glody_platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Đảm bảo sử dụng BCryptPasswordEncoder nguyên bản từ Spring Security
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(12);
    }

}
