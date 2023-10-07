package com.demo.auth.service;

import com.demo.auth.dto.AuthResponseDto;
import com.demo.auth.dto.UserLoginRequestDto;
import com.demo.auth.dto.UserRegisterRequestDto;
import com.demo.auth.exception.UserNotFoundException;
import com.demo.auth.jwt.TokenService;
import com.demo.auth.mapper.UserMapper;
import com.demo.auth.user.User;
import com.demo.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  
  private final TokenService tokenService;
  
  private final UserMapper userMapper;
  
  private final AuthenticationManager authenticationManager;
  
  public AuthResponseDto register(UserRegisterRequestDto request) {
    User user = userRepository.save(userMapper.fromUserRegistrationRequestToUser(request));
    return AuthResponseDto.builder()
        .token(tokenService.generateToken(user))
        .build();
  }
  
  public AuthResponseDto login(UserLoginRequestDto request) {
    String email = request.getEmail();
    
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            email,
            request.getPassword()
        )
    );
    
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    
    return AuthResponseDto.builder()
        .token(tokenService.generateToken(user))
        .build();
  }
}
