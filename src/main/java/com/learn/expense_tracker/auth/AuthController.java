package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.request.AuthRequest;
import com.learn.expense_tracker.auth.request.RegisterRequest;
import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Login & Register")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Operation(summary = "Register")
  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest request) {
    return this.authService.register(request.getUsername(), request.getPassword());
  }

  @Operation(summary = "Login")
  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest request) {
    return this.authService.login(request.getUsername(), request.getPassword());
  }
}
