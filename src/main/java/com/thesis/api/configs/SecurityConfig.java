package com.thesis.api.configs;

import com.thesis.api.filters.AuditLoggingFilter;
import com.thesis.api.filters.RateLimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.ForceEagerSessionCreationFilter;

@Configuration
@Profile("dev")
@EnableWebSecurity(debug = false) // Debugging off for production environment
@EnableMethodSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS Enabled
                .cors().and()

                // Sessions disabled
                .sessionManagement().disable()

                // CSRF disabled (unnecessary with Bearer authentication)
                .csrf().disable()

                // Add CSP header
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'none' ; frame-ancestors 'none' ; sandbox")
                        )
                )

                // Security for HTTP requests enabled
                .authorizeHttpRequests(authorize -> authorize
                        // GET method for /api/v1/book is public
                        .requestMatchers(HttpMethod.GET,"/api/v1/book").permitAll()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Configuration for how to handle JWT authentication
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()
                        .jwtAuthenticationConverter(jwtRoleAuthenticationConverter())
                )

                // Add custom filters for audit logging and rate limiting
                .addFilterBefore(new RateLimitFilter(), ForceEagerSessionCreationFilter.class)
                .addFilterBefore(new AuditLoggingFilter(), BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    // Converts JWT claims to Spring security GrantedAuthority
    public JwtAuthenticationConverter jwtRoleAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Add authorities from roles claim
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        // Add ROLE_ prefix for authorities
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}