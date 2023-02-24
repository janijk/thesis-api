package com.thesis.api.filters;

import com.thesis.api.utils.LogWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
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
        LocalDateTime timePre = LocalDateTime.now();
        StringBuilder sbr = new StringBuilder("\nAuditLoggingFilter Pre:" +
                "\n     URI: " + request.getRequestURI() +
                "\n     METHOD: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + timePre.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        LogWriter.write(sbr.toString());
        System.out.println(sbr);

        // Call next filter of the chain
        filterChain.doFilter(request, response);

        // Log response
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = response.getHeader("www-authenticate");
        LocalDateTime timePost = LocalDateTime.now();

        // Log entry base data
        StringBuilder sb = new StringBuilder("\nAuditLoggingFilter Post:" +
                "\n     URI: " + request.getRequestURI() +
                "\n     METHOD: " + request.getMethod() +
                "\n     ORIGIN: " + request.getRemoteHost() +
                "\n     TIME: " + timePost.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "\n     STATUS: " + response.getStatus());

        // Log entry additional data
        if (auth != null && auth.contains("Bearer")){
            sb.append("\n     AUTH: " + (authentication != null? authentication.getAuthorities().toString() : null));
            sb.append("\n     VALIDATION: " + auth);

        } else {
            sb.append("\n     AUTH: " + authentication.getAuthorities().toString());
            sb.append("\n     USER: " + request.getRemoteUser());
        }

        LogWriter.write(sb.toString());
        System.out.println(sb);
    }
}