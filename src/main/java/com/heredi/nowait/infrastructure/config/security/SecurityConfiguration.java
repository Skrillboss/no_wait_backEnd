package com.heredi.nowait.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfiguration(JwtAuthFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register",
                                "/user/login",
                                "/user/verifyEmail/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/business/**",
                                "/item/{itemId}/sendQR/mail",
                                "/item/create").access((authentication, object) -> {
                                    boolean hasAdminRole = authentication.get().getAuthorities().stream()
                                            .anyMatch(grantedAuthority -> grantedAuthority.
                                                    getAuthority().equals("ROLE_ADMIN"));
                                    boolean hasStatusActive = authentication.get().getAuthorities().stream()
                                            .anyMatch(grantedAuthority -> grantedAuthority.
                                                    getAuthority().equals("STATUS_ACTIVE"));
                                    return new AuthorizationDecision(hasAdminRole && hasStatusActive);
                        })
                        .anyRequest().hasAuthority("STATUS_ACTIVE")
                )
                .userDetailsService(userDetailsService)
                .httpBasic(withDefaults());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}