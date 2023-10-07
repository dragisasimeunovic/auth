package com.demo.auth.exception;

public class UserNotFoundException extends RuntimeException {
  
  public UserNotFoundException(String email) {
    super("Cannot find user " + email);
  }
}
