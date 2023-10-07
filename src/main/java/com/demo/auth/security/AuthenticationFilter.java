package com.demo.auth.security;

import com.demo.auth.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
  
  private static final String BEARER = "Bearer ";
  
  private TokenService tokenService;
  
  private UserDetailsService userDetailsService;
  
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
  
    // JWT token is located after 'Bearer '
    String token = authorizationHeader.substring(BEARER.length());
    String email = tokenService.getUsername(token);
    // authenticate user if it's not authenticated
    if (StringUtils.hasText(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    }
  }
}
