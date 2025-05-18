package com.security.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationEvents {

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent successEvent) {
    log.info("Login success: {}", successEvent.getAuthentication().getName());
  }

  @EventListener
  public void onFailure(AuthorizationDeniedEvent deniedEvent) {
    log.error("Authorization failed user : {} to : {}", deniedEvent.getAuthentication().get().getName(), deniedEvent.getAuthorizationResult()
        .toString());
  }
}
