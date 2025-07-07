package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    private String password;
    
    @NotBlank(message = "role must not be blank")
    private String role;
}
