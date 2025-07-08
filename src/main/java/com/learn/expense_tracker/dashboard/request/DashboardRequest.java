package com.learn.expense_tracker.dashboard.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DashboardRequest {
    @NotBlank(message = "Start date must not be blank")
    private String from;
    @NotBlank(message = "End date must not be blank")
    private String to;
}
