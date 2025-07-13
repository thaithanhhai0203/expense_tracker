package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.mapper.UserMapper;
import com.learn.expense_tracker.user.request.ChangePwRequest;
import com.learn.expense_tracker.user.request.ResetPwRequest;
import com.learn.expense_tracker.user.request.UpdateRequest;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public List<UserResponse> findAll() {
    List<User> users = this.userRepository.findAll();
    return users.stream().map(UserMapper::toResponse).toList();
  }

  public UserResponse getById(Long id) {
    User user =
            this.userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    return UserMapper.toResponse(user);
  }

  public UserResponse getMe(String token){
    Long userId = jwtService.extractUserId(token);
    User user =
            this.userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    return UserMapper.toResponse(user);
  }

  public SuccessCode save(UserRequest userRequest) {
    this.userRepository.save(UserMapper.toEntity(userRequest));
    return SuccessCode.USER_CREATED;
  }

  public SuccessCode update(UpdateRequest updateRequest, String token) {
  Long userId = jwtService.extractUserId(token);
  User foundUser =
        this.userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

  if (this.userRepository.existsByEmail(updateRequest.getEmail())) {
      throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
  }
  foundUser.setUsername(updateRequest.getUsername());
  foundUser.setEmail(updateRequest.getEmail());
  foundUser.setAvatar(updateRequest.getAvatar());
  this.userRepository.save(foundUser);
  return SuccessCode.USER_UPDATED;
  }

  public SuccessCode changePw(ChangePwRequest changePwRequest, String token) {
    Long userId = jwtService.extractUserId(token);
    User foundUser =
            this.userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(changePwRequest.getOldPw(), foundUser.getPassword())) {
      throw new ApiException(ErrorCode.INVALID_OLD_PASSWORD);
    }
    foundUser.setPassword(passwordEncoder.encode(changePwRequest.getNewPw()));
    this.userRepository.save(foundUser);
    return SuccessCode.USER_UPDATED;
  }

  public SuccessCode resetPw(ResetPwRequest resetPwRequest) {
    if (!this.userRepository.existsByEmail(resetPwRequest.getEmail())) {
      throw new ApiException(ErrorCode.EMAIL_NOT_REGISTERED);
    }
    User foundUser = this.userRepository.findByEmail(resetPwRequest.getEmail()).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    return SuccessCode.USER_UPDATED;
  }
}
