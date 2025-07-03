package com.learn.expense_tracker.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

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
  public List<User> getAll() {
    return userService.findAll();
  }

  @Operation(summary = "Add user")
  @PostMapping
  public User create(@RequestBody User user) {
    return userService.save(user);
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public User getById(@PathVariable Long id) {
    return userService.getById(id);
  }
}
