package com.demo.auth.mapper;

import com.demo.auth.dto.UserRegisterRequestDto;
import com.demo.auth.user.Role;
import com.demo.auth.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  
  private final PasswordEncoder passwordEncoder;
  
  public User fromUserRegistrationRequestToUser(UserRegisterRequestDto registerRequest) {
    return User.builder()
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .firstname(registerRequest.getFirstname())
        .lastname(registerRequest.getLastname())
        .role(Role.USER)
        .build();
  }
  
}
