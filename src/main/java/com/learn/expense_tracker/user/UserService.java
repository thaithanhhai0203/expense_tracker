package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.exception.ApiException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new ApiException("User not found"));
  }
}
