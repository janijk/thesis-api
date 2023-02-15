package com.thesis.api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
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
                "\n     TIME: " + timePre.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "\n     TOKEN: " + request.getHeader("Authorization"));

        filterChain.doFilter(request, response);
        Principal authentication = SecurityContextHolder.getContext().getAuthentication();

        LocalDateTime timePost = LocalDateTime.now();
        String auth = response.getHeader("www-authenticate");

        if (auth != null && auth.contains("Bearer")){
            System.out.println("\nAuditLoggingFilter Post:" +
                    "\n     URI: " + request.getRequestURI() +
                    "\n     METHOD: " + request.getMethod() +
                    "\n     ORIGIN: " + request.getRemoteHost() +
                    "\n     TIME: " + timePost.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n     TOKEN: " + request.getHeader("Authorization") +
                    "\n     STATUS: " + response.getStatus() +
                    "\n     AUTH: " + authentication +
                    "\n     VALIDATION: " + response.getHeader("www-authenticate"));
        }
    }
}