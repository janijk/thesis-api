package com.thesis.api.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvent {
    @EventListener
    public void onFailure(AuthorizationDeniedEvent failure) {
        System.out.println("**********\nAUTHORIZATION EVENT LISTENER:");
        System.out.println(failure.getAuthorizationDecision());
        System.out.println(failure.getAuthentication().get());
        System.out.println("**********");
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        System.out.println("**********\nAUTHENTICATION EVENT LISTENER:");
        System.out.println(failures.toString());
        System.out.println(failures.getAuthentication());
        System.out.println("**********");
    }

}
