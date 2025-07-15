package com.learn.expense_tracker.budget.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BudgetRequest {
  @NotBlank(message = "Amount limit not be blank")
  @Min(value = 1, message = "Amount limit must be greater than 0")
  private Long amountLimit;

  @NotBlank(message = "Type limit not be blank")
  @Min(value = 1, message = "Amount limit must be greater than 0")
  private int type;

  @NotBlank(message = "Category id not be blank")
  @Min(value = 1, message = "Category id limit must be greater than 0")
  private Long categoryId;
}
