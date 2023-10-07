package com.demo.auth.dto;

import lombok.*;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
  
  @NonNull
  private String firstname;
  
  @NonNull
  private String lastname;
  
  @NonNull
  private String email;
  
  @NonNull
  private String password;
}
