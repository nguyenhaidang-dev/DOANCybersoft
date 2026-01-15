package com.datn.drugstore.config;

import com.datn.drugstore.filter.AuthenticationFilter;
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

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
            http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/", "/api/users/login", "/api/users/register", "/api/users/get-only-email/**",
                                "/uploads/**",
                                "/api/products", "/api/products/search/**", "/api/products/searchHere/**",
                                "/api/products/all-prescription", "/api/products/search-prescription/**",
                                "/api/products/{id}",
                                "/api/category/all", "/api/category/all/status", "/api/category/all/status/no",
                                "/api/category/all/status-detail/**", "/api/category/{id}",
                                "/api/category/all/banner", "/api/category/banner-detail/**",
                                "/api/pdf/all", "/api/pdf/{id}", "/api/pdf/searchpdf/**",
                                "/api/orders/prescription-order", "/api/orders/order-repair",
                                "/api/config/paypal"
                        ).permitAll()
                        .requestMatchers("POST", "/api/users").permitAll()
                        .requestMatchers("GET", "/api/users").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/users/profile", "/api/users/{id}", "/api/orders", "/api/orders/{id}",
                                "/api/orders/{id}/pay",
                                "/api/orders/{id}/delivered"
                        ).authenticated()
                        .requestMatchers(
                                "/api/products/create", "/api/products/update/**", "/api/products/delete/**",
                                "/api/orders/all",
                                "/api/orders/search/**", "/api/orders/status/**",
                                "/api/orders/option/**", "/api/orders/combine/**",
                                "/api/orders/filter/**", 
                                "/api/category/create", "/api/category/update/**", "/api/category/delete/**",
                                "/api/pdf/create", "/api/pdf/update/**", "/api/pdf/delete/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
