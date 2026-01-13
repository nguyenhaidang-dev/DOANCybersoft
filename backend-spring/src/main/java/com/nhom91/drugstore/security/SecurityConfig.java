package com.nhom91.drugstore.security;

import com.nhom91.drugstore.repository.UserRepository;
import com.nhom91.drugstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtils, userRepository);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "http://localhost:3001", "http://localhost:4000", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/", "/api/users/login", "/api/users", "/api/users/get-only-email/**",
                            "/api/products", "/api/products/search/**", "/api/products/all-prescription",
                            "/api/products/search-prescription/**", "/api/products/{id}",
                            "/api/category/all", "/api/category/all/status", "/api/category/all/status/no",
                            "/api/category/all/status-detail/**", "/api/category/{id}",
                            "/api/category/all/banner", "/api/category/banner-detail/**",
                            "/api/pdf/all", "/api/pdf/{id}", "/api/pdf/searchpdf/**",
                            "/api/orders/prescription-order", "/api/orders/order-repair",
                            "/api/config/paypal").permitAll()
                    .requestMatchers("/api/users/profile", "/api/orders", "/api/orders/{id}").authenticated()
                    .requestMatchers("/api/users", "/api/products/**", "/api/orders/all", "/api/orders/{id}/pay",
                            "/api/orders/{id}/delivered", "/api/orders/search/**", "/api/orders/status/**",
                            "/api/orders/option/**", "/api/orders/combine/**", "/api/orders/filter/**",
                            "/api/category/**", "/api/pdf/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            );

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
    // Migrated from NodeJS AuthMiddleware with JWT
}