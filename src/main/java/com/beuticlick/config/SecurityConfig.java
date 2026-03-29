package com.beuticlick.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beuticlick.security.JwtAuthFilter;
import com.beuticlick.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Public — auth + Swagger UI
                .requestMatchers(
                    "/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs"
                ).permitAll()

                // Customers
                .requestMatchers("GET",  "/services/active").hasAnyRole("CUSTOMER", "STAFF", "SALON_ADMIN")
                .requestMatchers("POST", "/appointments").hasAnyRole("CUSTOMER", "STAFF", "SALON_ADMIN")
                .requestMatchers("GET",  "/appointments/customer/**").hasAnyRole("CUSTOMER", "SALON_ADMIN")

                // Staff
                .requestMatchers("GET",   "/appointments/**").hasAnyRole("STAFF", "SALON_ADMIN")
                .requestMatchers("PATCH", "/appointments/**").hasAnyRole("STAFF", "SALON_ADMIN")
                .requestMatchers("GET",   "/customers/**").hasAnyRole("STAFF", "SALON_ADMIN")

                // Salon admin only
                .requestMatchers("/staff/**").hasRole("SALON_ADMIN")
                .requestMatchers("/services/**").hasRole("SALON_ADMIN")
                .requestMatchers("POST", "/customers/**").hasAnyRole("STAFF", "SALON_ADMIN")

                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
