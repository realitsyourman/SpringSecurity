package com.security.spring.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.security.spring.exceptionhandling.CustomeAccessDeniedHandler;
import com.security.spring.exceptionhandling.CustomeBasicAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L);

            return config;
          }
        }))
        .requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1)
            .maxSessionsPreventsLogin(true))
        .authorizeHttpRequests(
            request -> request
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user")
                .authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession")
                .permitAll())
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
