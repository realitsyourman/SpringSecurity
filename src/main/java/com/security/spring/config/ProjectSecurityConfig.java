package com.security.spring.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.security.spring.exceptionhandling.CustomeAccessDeniedHandler;
import com.security.spring.exceptionhandling.CustomeBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
        .authorizeHttpRequests(
            request -> request
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession").permitAll())
        .formLogin(withDefaults())
        .httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomeBasicAuthenticationEntryPoint()))
        .exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomeAccessDeniedHandler()))
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
