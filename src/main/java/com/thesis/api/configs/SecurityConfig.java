package com.thesis.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS Enabled
                .cors().and()
                // Sessions disabled
                .sessionManagement().disable()
                // CSRF disabled
                .csrf().disable()
                // Security for HTTP requests enabled
                .authorizeHttpRequests(authorize -> authorize
                            // All endpoints require authentication
                            .anyRequest().authenticated()
                )
                // OAuth 2.0 resource server config
                .oauth2ResourceServer(oauth2 ->{
                    // Convert Jwt to AbstractAuthenticationToken
                    JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();

                    // Convert Jwt roles claim to GrantedAuthorities
                    JwtGrantedAuthoritiesConverter roleConverter = new JwtGrantedAuthoritiesConverter();
                    roleConverter.setAuthorityPrefix("ROLE_");
                    roleConverter.setAuthoritiesClaimName("roles");

                    // Jwt -> GrantedAuthorities -> AbstractAuthenticationToken
                    authConverter.setJwtGrantedAuthoritiesConverter(roleConverter);

                    // JWT authentication and AC from claims enabled
                    oauth2.jwt().jwtAuthenticationConverter(authConverter);
                });
        return http.build();
    }

}
