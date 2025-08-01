package com.glody.glody_platform.security;

import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        System.out.println("===== JwtAuthFilter =====");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Token: " + token);

        if (token != null && jwtTokenUtil.validateToken(token)) {
            Long userId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
            User user = userRepository.findById(userId).orElse(null);

            System.out.println("UserID from token: " + userId);

            if (user != null && Boolean.FALSE.equals(user.getIsDeleted())) {
                List<String> roles = jwtTokenUtil.getRolesFromToken(token);
                System.out.println("Roles from token: " + roles);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                        .peek(role -> System.out.println("Granted Authority: " + role))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("Authentication set with email: " + user.getEmail());
            } else {
                System.out.println("User not found or deleted");
            }
        } else {
            System.out.println("Token is invalid or missing");
        }

        filterChain.doFilter(request, response);
    }
}
