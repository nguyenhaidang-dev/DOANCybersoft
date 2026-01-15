package com.datn.drugstore.filter;

import com.datn.drugstore.entity.User;
import com.datn.drugstore.repository.UserRepository;
import com.datn.drugstore.service.TokenSessionService;
import com.datn.drugstore.utils.JWTHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;
    private final UserRepository userRepository;
    private final TokenSessionService tokenSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                try {
                    jwtHelper.validateToken(token);
                    Long userId = jwtHelper.extractUserId(token);
                    Optional<User> userOpt = userRepository.findById(userId);
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        if (!tokenSessionService.isValidSession(user.getEmail(), token)) {
                            logger.warn("Invalid session. Token has been replaced by another login");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            // Return BaseResponse format for consistency
                            response.getWriter().write("{\"code\": 401, \"message\": \"Session expired. You have been logged in from another device. Please login again.\", \"data\": null}");
                            return;
                        }
                        
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(user, null, null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    logger.warn("Invalid or expired JWT token");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    // Return BaseResponse format for consistency
                    response.getWriter().write("{\"code\": 401, \"message\": \"Invalid or expired token. Please login again.\", \"data\": null}");
                    return;
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }
}
