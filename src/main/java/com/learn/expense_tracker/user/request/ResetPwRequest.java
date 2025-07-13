package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPwRequest {
    @NotBlank(message = "email must not be blank")
    private String email;
}
