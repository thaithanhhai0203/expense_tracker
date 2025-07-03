package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ApiResponse<List<User>>> getAll() {
    return ResponseEntity.ok(ApiResponse.success(userService.findAll(), "Fetch users"));
  }

  @Operation(summary = "Add user")
  @PostMapping
  public ResponseEntity<ApiResponse<User>> create(@RequestBody User user) {
    return ResponseEntity.ok(ApiResponse.success(userService.save(user), "User created"));
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<User>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(userService.getById(id), "User found"));
  }
}
