package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPwRequest {
  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email is not valid")
  private String email;
}
