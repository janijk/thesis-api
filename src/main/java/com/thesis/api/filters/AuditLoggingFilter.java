package com.thesis.api.filters;

import com.thesis.api.utils.LogWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Log request
        StringBuilder sbr = new StringBuilder("\nAuditLoggingFilter Pre:");
        sbr.append("\n     URI: ").append(request.getRequestURI())
                .append("\n     METHOD: ").append(request.getMethod())
                .append("\n     ORIGIN: ").append(request.getRemoteHost())
                .append("\n     TIME: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // Write to file
        LogWriter.write(sbr.toString());
        System.out.println(sbr);

        // Call next filter of the chain
        filterChain.doFilter(request, response);

        // Log response
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String challenge = response.getHeader("www-authenticate");

        // Log entry base data
        StringBuilder sb = new StringBuilder("\nAuditLoggingFilter Post:");
        sb.append("\n     URI: ").append(request.getRequestURI())
                .append("\n     METHOD: ").append(request.getMethod())
                .append("\n     ORIGIN: ").append(request.getRemoteHost())
                .append("\n     TIME: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .append("\n     STATUS: ").append(response.getStatus())
                .append("\n     USER: ").append(auth != null ? auth.getName() : null)
                .append("\n     AUTH: ").append(auth != null ? auth.getAuthorities() : null);

        // Log entry additional data on authentication failure
        if (challenge != null){
            sb.append("\n     MSG: ")
                    .append(challenge.contains("Bearer error") ? challenge : "Bearer token not provided");
        }

        // Write to file
        LogWriter.write(sb.toString());
        System.out.println(sb);
    }
}