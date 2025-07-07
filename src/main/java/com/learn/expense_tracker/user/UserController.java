package com.learn.expense_tracker.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;

@Tag(name = "User", description = "Info User")
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get user list")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponse> getAll() {
    return userService.findAll();
  }

  @Operation(summary = "Add user")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String create(@RequestBody UserRequest userRequest) {
    return userService.save(userRequest);
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse getById(@PathVariable Long id) {
    return userService.getById(id);
  }
}
