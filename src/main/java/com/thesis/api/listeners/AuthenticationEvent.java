package com.thesis.api.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvent {
    @EventListener
    public void onFailure(AuthorizationDeniedEvent failure) {
        System.out.println("*******************************\nAUTHORIZATION EVENT LISTENER:");
        System.out.println(failure.getAuthorizationDecision());
        System.out.println(failure.getAuthentication().get());
        //System.out.println("TIME:"+failure.getTimestamp());
        System.out.println("*******************************");
    }
}
