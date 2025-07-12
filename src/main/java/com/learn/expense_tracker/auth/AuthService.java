package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authManager,
      JwtService jwtService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthResponse login(String username, String password) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ApiException(ErrorCode.INVALID_USERNAME_OR_PASSWORD));
    Boolean isMatchPassword =
        passwordEncoder.matches(password, user.getPassword());
    if (!isMatchPassword) {
      throw new ApiException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = jwtService.generateToken(user);
    return new AuthResponse(token);
  }

  public SuccessCode register(String username, String password) {
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(Set.of("ROLE_USER"));

    userRepository.save(user);
    return SuccessCode.USER_REGISTERED;
  }
}
