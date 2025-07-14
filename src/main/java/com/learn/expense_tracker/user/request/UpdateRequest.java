package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRequest {
  @NotBlank(message = "username must not be blank")
  private String username;

  @NotBlank(message = "email must not be blank")
  @Email(message = "email is not valid")
  private String email;

  @NotBlank(message = "avatar must not be blank")
  private String avatar;

  @NotBlank(message = "role must not be blank")
  private String role;
}
