package com.thesis.api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        System.out.println("\nAuditLoggingFilter Pre:");
        System.out.println("     URI: " + request.getRequestURI() +
                "\n     Method: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + timePre.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "\n     TOKEN: " + request.getHeader("Authorization"));

        filterChain.doFilter(request, response);

        LocalDateTime timePost = LocalDateTime.now();
        String auth = response.getHeader("www-authenticate");

        if (auth != null && auth.contains("Bearer")){
            System.out.println("\nAuditLoggingFilter Post:");
            System.out.println("     URI: " + request.getRequestURI() +
                    "\n     Method: " + request.getMethod() +
                    "\n     ORIGIN: " + request.getRemoteHost() +
                    "\n     TIME: " + timePost.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n     TOKEN: " + request.getHeader("Authorization") +
                    "\n     STATUS: " + response.getStatus() +
                    "\n     VALIDATION: " + response.getHeader("www-authenticate"));
        }
    }
}