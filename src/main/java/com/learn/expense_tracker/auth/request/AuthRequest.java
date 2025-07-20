package com.learn.expense_tracker.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
  @NotBlank(message = "email must not be blank")
  private String email;

  @NotBlank(message = "password must not be blank")
  private String password;
}
