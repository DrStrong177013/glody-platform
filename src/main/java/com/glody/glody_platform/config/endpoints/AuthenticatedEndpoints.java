package com.glody.glody_platform.config.endpoints;

public class AuthenticatedEndpoints {
    public static final String[] ALL_ENDPOINTS = {
            "/api/comments/**",
            "/api/chats/**",
            "/api/user-subscriptions/**",
            "/api/users/me/roles",
            "/api/feedbacks",
            "/api/invoices",
            "/api/invoices/**"

    };
}
