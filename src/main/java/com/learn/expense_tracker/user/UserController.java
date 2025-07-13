package com.learn.expense_tracker.user;

import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.request.ChangePwRequest;
import com.learn.expense_tracker.user.request.ResetPwRequest;
import com.learn.expense_tracker.user.request.UpdateRequest;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
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
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponse> getAll() {
    return userService.findAll();
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse getById(@PathVariable Long id) {
    return userService.getById(id);
  }

  @Operation(summary = "Get me")
  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse getMe(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return userService.getMe(token);
  }

  @Operation(summary = "Add user")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SuccessCode create(@RequestBody UserRequest userRequest) {
    return this.userService.save(userRequest);
  }

  @Operation(summary = "Update my information")
  @PutMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public SuccessCode update(@RequestBody UpdateRequest userRequest, @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.userService.update(userRequest, token);
  }

  @Operation(summary = "Change Password")
  @PutMapping("/change-password")
  @ResponseStatus(HttpStatus.OK)
  public SuccessCode changePw(@RequestBody ChangePwRequest changePwRequest, @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.userService.changePw(changePwRequest, token);
  }

  @Operation(summary = "Reset Password")
  @PutMapping("/reset-password")
  @ResponseStatus(HttpStatus.OK)
  public SuccessCode resetPw(@RequestBody ResetPwRequest resetPwRequest) {
    return this.userService.resetPw(resetPwRequest);
  }
}
