package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.user.mapper.UserMapper;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserResponse> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream().map(UserMapper::toResponse).toList();
  }

  public String save(UserRequest user) {
    User savedUser = userRepository.save(UserMapper.toEntity(user));
    return "User saved with ID: " + savedUser.getId();
  }

  public UserResponse getById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ApiException("User not found", HttpStatus.BAD_REQUEST));
    return UserMapper.toResponse(user);
  }
}
