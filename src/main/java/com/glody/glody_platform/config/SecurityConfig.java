package com.glody.glody_platform.config;

import com.glody.glody_platform.config.endpoints.*;
import com.glody.glody_platform.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // since we’re a pure JSON/JWT API
                .csrf(csrf -> csrf.disable())

                // require HTTPS everywhere
//                .requiresChannel(channel -> channel.anyRequest().requiresSecure())

                // stateless session (no HttpSession)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // hook in your JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // authorization rules
                .authorizeHttpRequests(auth -> auth
                        // 1) explicitly permit your auth URLs
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2) public GET/POST (if you have other public);
                        //    you can still keep your existing arrays here
                        .requestMatchers(HttpMethod.GET, PublicEndpoints.GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, PublicEndpoints.POST_ENDPOINTS).permitAll()

                        // 3) role-based
                        .requestMatchers(StudentEndpoints.ALL_ENDPOINTS).hasRole("STUDENT")
                        .requestMatchers(ExpertEndpoints.ALL_ENDPOINTS).hasRole("EXPERT")
                        .requestMatchers(AdminEndpoints.ALL_ENDPOINTS).hasRole("ADMIN")

                        // 4) everything else needs authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }


}
