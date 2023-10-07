package com.demo.auth;

import com.demo.auth.dto.AuthResponseDto;
import com.demo.auth.dto.UserLoginRequestDto;
import com.demo.auth.dto.UserRegisterRequestDto;
import com.demo.auth.jwt.TokenService;
import com.demo.auth.mapper.UserMapper;
import com.demo.auth.repository.UserRepository;
import com.demo.auth.service.AuthenticationService;
import com.demo.auth.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private TokenService tokenService;
  
  @Mock
  private UserMapper userMapper;
  
  @Mock
  private AuthenticationManager authenticationManager;
  
  @InjectMocks
  private AuthenticationService authenticationService;
  
  @Test
  void testRegister() {
    // Given
    UserRegisterRequestDto request = new UserRegisterRequestDto();
    User user = new User();
    String token = "testToken";
    
    when(userMapper.fromUserRegistrationRequestToUser(request)).thenReturn(user);
    when(userRepository.save(user)).thenReturn(user);
    when(tokenService.generateToken(user)).thenReturn(token);
    
    // When
    AuthResponseDto result = authenticationService.register(request);
    
    // Then
    verify(userMapper).fromUserRegistrationRequestToUser(request);
    verify(userRepository).save(user);
    verify(tokenService).generateToken(user);
    
    assertEquals(token, result.getToken());
  }
  
  @Test
  void testLogin() {
    // Given
    UserLoginRequestDto request = new UserLoginRequestDto();
    String email = "test@gmail.com";
    request.setEmail(email);
    String token = "testToken";
    
    Optional<User> userOptional = Optional.of(new User());
    
    when(userRepository.findByEmail(email)).thenReturn(userOptional);
    when(tokenService.generateToken(any(User.class))).thenReturn(token);
    
    // When
    AuthResponseDto result = authenticationService.login(request);
    
    // Then
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByEmail(email);
    verify(tokenService).generateToken(any(User.class));
    
    assertEquals(token, result.getToken());
  }
}
