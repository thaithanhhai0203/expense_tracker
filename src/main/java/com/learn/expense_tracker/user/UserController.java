package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.request.ChangePwRequest;
import com.learn.expense_tracker.user.request.UpdateRequest;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

  public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
  }

  @Operation(summary = "Get user list")
  @RequireRoles({"ADMIN"})
  @GetMapping
  public List<UserResponse> getAll() {
    return userService.findAll();
  }

  @Operation(summary = "Get user by id")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/{id}")
  public UserResponse getById(@PathVariable Long id) {
    return userService.getById(id);
  }

  @Operation(summary = "Get me")
  @GetMapping("/me")
  @RequireRoles({"ADMIN", "USER"})
  public UserResponse getMe(@Parameter(hidden = true) @CurrentUser User user) {
    return userService.getMe(user.getId());
  }

  @Operation(summary = "Add user")
  @PostMapping
  @RequireRoles({"ADMIN", "USER"})
  public SuccessCode create(@RequestBody UserRequest userRequest) {
    return this.userService.save(userRequest);
  }

  @Operation(summary = "Update my information")
  @PutMapping("/me")
  @RequireRoles({"ADMIN", "USER"})
  public SuccessCode update(@RequestBody UpdateRequest userRequest, @Parameter(hidden = true) @CurrentUser User user) {
    return this.userService.update(userRequest, user.getId());
  }

  @Operation(summary = "Change Password")
  @PutMapping("/change-password")
  @RequireRoles({"ADMIN", "USER"})
  public SuccessCode changePw(
      @RequestBody ChangePwRequest changePwRequest, @Parameter(hidden = true) @CurrentUser User user) {
    return this.userService.changePw(changePwRequest, user.getId());
  }
}
