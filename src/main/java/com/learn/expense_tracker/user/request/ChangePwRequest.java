package com.learn.expense_tracker.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePwRequest {
    @NotBlank(message = "old password must not be blank")
    private String oldPw;

    @NotBlank(message = "new password must not be blank")
    private String newPw;
}
