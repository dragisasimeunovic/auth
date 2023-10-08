package com.demo.auth.controller;

import com.demo.auth.dto.AuthResponseDto;
import com.demo.auth.dto.UserLoginRequestDto;
import com.demo.auth.dto.UserRegisterRequestDto;
import com.demo.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
  
  private final AuthenticationService authenticationService;
  
  /**
   * Registers a new user based on the provided registration request.
   *
   * @param request The user registration request containing user details.
   * @return An {@link AuthResponseDto} containing jwt token.
   */
  @PostMapping("/register")
  public AuthResponseDto register(@RequestBody UserRegisterRequestDto request) {
    return authenticationService.register(request);
  }
  
  /**
   * Logs in a user based on the provided login request.
   *
   * @param request The user login request containing login credentials.
   * @return An {@link AuthResponseDto} containing jwt token.
   */
  @PostMapping("/login")
  public AuthResponseDto login(@RequestBody UserLoginRequestDto request) {
    return authenticationService.login(request);
  }
}
