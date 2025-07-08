package com.learn.expense_tracker.dashboard.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DashboardRequest {
    @NotBlank(message = "Year-month must not be blank")
    private String yearMonth;

}
