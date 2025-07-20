package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.request.AuthRequest;
import com.learn.expense_tracker.auth.request.RegisterRequest;
import com.learn.expense_tracker.auth.request.ResetPasswordRequest;
import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.annotation.Public;
import com.learn.expense_tracker.common.dto.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
  @Public
  @PostMapping("/register")
  public SuccessCode register(@RequestBody RegisterRequest request) {
    return this.authService.register(request);
  }

  @Operation(summary = "Login")
  @Public
  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest request) {
    return this.authService.login(request.getEmail(), request.getPassword());
  }

  @Operation(summary = "Forgot Password")
  @Public
  @GetMapping("/forgot-password")
  public SuccessCode forgotPassword(@RequestParam("email") String email) {
    return this.authService.forgotPassword(email);
  }

  @Operation(summary = "Reset Password")
  @Public
  @PostMapping("/reset-password")
  public SuccessCode resetPassword(@RequestBody ResetPasswordRequest request) {
    return this.authService.resetPassword(request);
  }
}
