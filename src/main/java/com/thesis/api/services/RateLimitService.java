package com.thesis.api.services;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {
    private final static RateLimiter rateLimiter = RateLimiter.create(1d);

    public static boolean returnLimit(){
        return rateLimiter.tryAcquire();
    }

}
