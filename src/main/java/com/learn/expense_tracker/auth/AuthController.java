package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.request.AuthRequest;
import com.learn.expense_tracker.auth.request.RegisterRequest;
import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.register(request.username(), request.password()), "Register success"));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request.username(), request.password()), "Login success"));
    }
}

