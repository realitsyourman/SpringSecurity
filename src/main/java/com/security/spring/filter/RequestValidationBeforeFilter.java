package com.security.spring.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

public class RequestValidationBeforeFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String header = req.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null) {
      header = header.trim();
      if (StringUtils.startsWithIgnoreCase(header, "Basic ")) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decode;
        try {
          decode = Base64.getDecoder().decode(base64Token);
          String token = new String(decode, StandardCharsets.UTF_8);

          int delim = token.indexOf(":");
          if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
          }
          String email = token.substring(0, delim);
          if (email.toLowerCase().contains("test")) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
          }
        } catch (IllegalArgumentException e) {
          throw new BadCredentialsException("Fail to decode authentication token");
        }
      }
    }

    chain.doFilter(request, response);
  }
}
