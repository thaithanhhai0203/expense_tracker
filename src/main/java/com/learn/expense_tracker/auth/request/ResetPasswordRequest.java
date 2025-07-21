package com.learn.expense_tracker.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
  @NotBlank(message = "token must not be blank")
  private String token;

  @NotBlank(message = "newPassword must not be blank")
  private String newPassword;
}
