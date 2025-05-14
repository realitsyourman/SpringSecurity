package com.security.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationEvents {

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent successEvent) {
    log.info("Login success: {}", successEvent.getAuthentication().getName());
  }

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
    log.error("Login failure: {} by: {}", failureEvent.getAuthentication().getName(),
        failureEvent.getException().getMessage());
  }
}
