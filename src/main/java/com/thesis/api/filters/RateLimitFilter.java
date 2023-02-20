package com.thesis.api.filters;

import com.thesis.api.services.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RateLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        boolean limit = RateLimitService.returnLimit();

        // If rate limit is exceeded respond with 429 Too many requests
        if (!limit){
            LocalDateTime time = LocalDateTime.now();

            StringBuilder sb = new StringBuilder();
            sb.append("{" +
                    "\"timestamp\": \""+ time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\"," +
                    "\"status\": 429," +
                    "\"message\": \"Too Many Requests\"," +
                    "\"path\": \"" + request.getRequestURI() + "\"" +
                    "}");

            response.resetBuffer();
            response.setStatus(429);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().print(sb.toString());
            response.flushBuffer();

        // If request is within rate limit continue to downstream filters
        }else{
            filterChain.doFilter(request,response);
        }
    }
}