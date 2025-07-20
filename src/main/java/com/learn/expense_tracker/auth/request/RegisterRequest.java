package com.learn.expense_tracker.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotBlank(message = "email must not be blank")
  private String email;

  @NotBlank(message = "username must not be blank")
  private String username;

  @NotBlank(message = "password must not be blank")
  private String password;

  @NotBlank(message = "avatar must not be blank")
  private String avatar;
}
