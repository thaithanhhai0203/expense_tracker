package com.learn.expense_tracker.auth;

import com.learn.expense_tracker.auth.request.RegisterRequest;
import com.learn.expense_tracker.auth.request.ResetPasswordRequest;
import com.learn.expense_tracker.auth.response.AuthResponse;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.provider.EmailService;
import com.learn.expense_tracker.reset_password.ResetPassword;
import com.learn.expense_tracker.reset_password.ResetPasswordRepository;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final ResetPasswordRepository resetPasswordRepository;
  private final EmailService emailService;

  @Value("${spring.mail.web-url}")
  private String webUrl;

  public AuthService(
      UserRepository userRepository,
      JwtService jwtService,
      PasswordEncoder passwordEncoder,
      ResetPasswordRepository resetPasswordRepository,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.resetPasswordRepository = resetPasswordRepository;
    this.emailService = emailService;
  }

  public AuthResponse login(String email, String password) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ApiException(ErrorCode.INVALID_USERNAME_OR_PASSWORD));
    Boolean isMatchPassword = passwordEncoder.matches(password, user.getPassword());
    if (!isMatchPassword) {
      throw new ApiException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
    }
    String token = jwtService.generateToken(user);
    return new AuthResponse(token);
  }

  public SuccessCode register(RegisterRequest request) {
    Optional<User> existingUser = userRepository.findByUsername(request.getEmail());
    if (existingUser.isPresent()) {
      throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
    }
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setAvatar(request.getAvatar());
    user.setRole(Set.of("ROLE_USER"));

    userRepository.save(user);
    return SuccessCode.USER_REGISTERED;
  }

  public SuccessCode forgotPassword(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    String token = UUID.randomUUID().toString();
    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setToken(token);
    resetPassword.setEmail(user.getEmail());
    resetPassword.setExpiryDate(LocalDateTime.now().plusHours(1));
    resetPasswordRepository.save(resetPassword);

    String resetLink = webUrl + "/reset-password?token=" + token;

    this.emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

    return SuccessCode.PASSWORD_RESET_EMAIL_SENT;
  }

  public SuccessCode resetPassword(ResetPasswordRequest request) {
    ResetPassword resetToken =
        resetPasswordRepository
            .findByToken(request.getToken())
            .orElseThrow(() -> new ApiException(ErrorCode.INVALID_RESET_TOKEN));
    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new ApiException(ErrorCode.RESET_TOKEN_EXPIRED);
    }

    User user =
        this.userRepository
            .findByEmail(resetToken.getEmail())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    this.userRepository.save(user);

    this.resetPasswordRepository.delete(resetToken);

    return SuccessCode.PASSWORD_RESET_SUCCESSFUL;
  }
}
