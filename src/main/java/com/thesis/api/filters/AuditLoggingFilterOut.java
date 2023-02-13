package com.thesis.api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        System.out.println("\nAuditLoggingFilterOut:");
        System.out.println("     URI: " + request.getRequestURI() +
                "\n     Method: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "\n     TOKEN: " + request.getHeader("Authorization") +
                "\n     STATUS: " + response.getStatus() +
                "\n     PRINCIPAL: " + request.getUserPrincipal() +
                "\n     USER: " + request.getRemoteUser());
    }
}
