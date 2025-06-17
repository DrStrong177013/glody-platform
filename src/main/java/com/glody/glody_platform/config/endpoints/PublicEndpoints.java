package com.glody.glody_platform.config.endpoints;

public class PublicEndpoints {
    public static final String[] GET_ENDPOINTS = {
            "/api/auth/**",
            "/api/users/register",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/posts",
            "/api/posts/slug/*/view",
            "/api/programs",
            "/api/programs/*",
            "/api/schools",
            "/api/schools/*",
            "/api/experts",
            "/api/categories",
            "/api/subscription-packages",
            "/api/subscription-packages/*",
            "/api/scholarships",
            "/api/scholarships/*",
            "/api/countries",
            "/api/countries/*",
            "/api/payments/*"

    };

    public static final String[] POST_ENDPOINTS = {
            "/api/appointments/public",
            "/api/auth/login",
            "/api/users/register",
    };
}
