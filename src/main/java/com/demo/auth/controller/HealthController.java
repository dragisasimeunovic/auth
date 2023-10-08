package com.demo.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {
  
  /**
   * Checks the health of the server.
   *
   * @return A string message indicating that the server is up and running.
   */
  @GetMapping("/health")
  public String health() {
    return "Server is up and running!";
  }
}
