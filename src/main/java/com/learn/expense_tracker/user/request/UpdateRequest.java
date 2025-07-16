package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRequest {
  @NotBlank(message = "Username must not be blank")
  private String username;

  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email is not valid")
  private String email;

  @NotBlank(message = "Avatar must not be blank")
  private String avatar;

  @NotBlank(message = "Role must not be blank")
  private String role;
}
