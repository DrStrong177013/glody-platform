package com.glody.glody_platform.config.endpoints;

public class AdminEndpoints {
    public static final String[] ALL_ENDPOINTS = {
            "/api/posts/admin/**",
            "/api/programs/admin/**",
            "/api/users/**",
            "/api/roles/**",
            "/api/admin/**",
            "/api/categories/**",
            "/api/program-requirements/**",
            "/api/system-logs",
            "/api/ai-logs",
            "/api/schools/admin/**",
            "/api/scholarships/admin/**",
            "/api/subscription-packages/**",
            "/api/tags/**",


            // All Expert Endpoints
            "/api/expert-profiles/**",
            "/api/appointments/expert/**",
            "/api/consultation-notes/**",

            // All Student Endpoints
            "/api/language-certificates/**",
            "/api/appointments",
            "/api/appointments/user/**",
            "/api/user-profiles/**",
            "/api/matching-history",
            "/api/programs/matching",
            "/api/scholarships/matching"
    };
}
