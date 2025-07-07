package com.learn.expense_tracker.budget.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {
    @NotBlank(message = "Amount limit not be blank")
    private Long amountLimit;

    @NotBlank(message = "Amount limit not be blank")
    private Long month;

    @NotBlank(message = "Category not be blank")
    private Long categoryId;
}
