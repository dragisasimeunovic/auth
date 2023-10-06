package com.demo.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
  
  private static final String BEARER = "Bearer ";
  
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    
    // check authentication header
    if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }
  
    // token is located after 'Bearer '
    String jwt = authorizationHeader.substring(BEARER.length());
  }
}
