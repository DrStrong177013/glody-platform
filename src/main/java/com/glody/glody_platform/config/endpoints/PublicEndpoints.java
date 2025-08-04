package com.glody.glody_platform.config.endpoints;

public class PublicEndpoints {
    public static final String[] GET_ENDPOINTS = {
            "/api/auth/**",

            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-ui/index.html**",
            "/favicon.ico",

            "/api/posts",
            "/api/posts/slug/*/view",
            "/api/comments/post/*",
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
            "/api/payment-return",
            "/api/create-payment",
            "/api/vnpay-ipn",
            "/api/tags",
            "/api/program-requirements/**",
            "/api/search/programs",
            "/api/search/programs/*"


    };

    public static final String[] POST_ENDPOINTS = {
            "/api/appointments/public",
            "/api/auth/login",
//            "/api/users/register",
    };
}
