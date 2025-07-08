package com.learn.expense_tracker.user.mapper;

import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;

public class UserMapper {
  public static User toEntity(UserRequest user) {
    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(user.getPassword());
    // Convert comma-separated roles string to Set<String>
    java.util.Set<String> roles = new java.util.HashSet<>();
    if (user.getRole() != null && !user.getRole().isEmpty()) {
      for (String role : user.getRole().split(",")) {
        roles.add(role.trim());
      }
    }
    newUser.setRole(roles);
    return newUser;
  }

  public static UserResponse toResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setRole(String.join(",", user.getRole()));
    response.setCreatedAt(user.getCreatedAt());
    return response;
  }
}
