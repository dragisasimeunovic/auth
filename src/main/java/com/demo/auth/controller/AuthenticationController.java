package com.demo.auth.controller;

import com.demo.auth.dto.AuthResponseDto;
import com.demo.auth.dto.UserLoginRequestDto;
import com.demo.auth.dto.UserRegisterRequestDto;
import com.demo.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
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
  
  @PostMapping("/register")
  public AuthResponseDto register(@RequestBody UserRegisterRequestDto request) {
    return authenticationService.register(request);
  }
  
  @PostMapping("/login")
  public AuthResponseDto login(@RequestBody UserLoginRequestDto request) {
    return authenticationService.login(request);
  }
}
