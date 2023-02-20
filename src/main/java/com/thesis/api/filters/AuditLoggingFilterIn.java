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

public class AuditLoggingFilterIn extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        LocalDateTime timePre = LocalDateTime.now();

        System.out.println("\nAuditLoggingFilter Pre:" +
                "\n     URI: " + request.getRequestURI() +
                "\n     METHOD: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + timePre.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        filterChain.doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = response.getHeader("www-authenticate");
        LocalDateTime timePost = LocalDateTime.now();

        if (auth != null && auth.contains("Bearer")){
            System.out.println("\nAuditLoggingFilter Post:" +
                    "\n     URI: " + request.getRequestURI() +
                    "\n     METHOD: " + request.getMethod() +
                    "\n     ORIGIN: " + request.getRemoteHost() +
                    "\n     TIME: " + timePost.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n     STATUS: " + response.getStatus() +
                    "\n     AUTH: " + (authentication!=null? authentication.getAuthorities().toString() : null) +
                    "\n     VALIDATION: " + response.getHeader("www-authenticate"));
        }
    }
}