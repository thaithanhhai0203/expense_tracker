package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final AuthenticationManager authManager;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authManager,
      JwtService jwtService,
      PasswordEncoder passwordEncoder) {
    this.authManager = authManager;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthResponse login(String username, String password) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED));
    String token = jwtService.generateToken(user);
    return new AuthResponse(token);
  }

  public String register(String username, String password) {
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new ApiException("User already exists", HttpStatus.BAD_REQUEST);
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(Set.of("ROLE_USER"));

    userRepository.save(user);
    return "Registered successfully";
  }
}
