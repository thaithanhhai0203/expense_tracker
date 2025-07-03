package com.learn.expense_tracker.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
  @NotBlank(message = "username must not be blank")
  private String username;

  @NotBlank(message = "password must not be blank")
  private String password;
}
