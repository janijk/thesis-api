package com.thesis.api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLoggingFilterOut extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        filterChain.doFilter(request, response);
        LocalDateTime time = LocalDateTime.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("\nAuditLoggingFilterOut:" +
                "\n     URI: " + request.getRequestURI() +
                "\n     METHOD: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "\n     TOKEN: " + request.getHeader("Authorization") +
                "\n     STATUS: " + response.getStatus() +
                "\n     AUTH: " + auth.getAuthorities().toString() +
                "\n     USER: " + request.getRemoteUser());
    }
}
