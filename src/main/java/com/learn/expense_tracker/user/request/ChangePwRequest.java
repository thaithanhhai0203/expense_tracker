package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePwRequest {
  @NotBlank(message = "Old password must not be blank")
  private String oldPw;

  @NotBlank(message = "New password must not be blank")
  private String newPw;
}
