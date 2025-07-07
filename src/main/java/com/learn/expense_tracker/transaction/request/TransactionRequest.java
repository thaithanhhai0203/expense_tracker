package com.learn.expense_tracker.transaction.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionRequest {
  @NotBlank(message = "Amount not be blank")
  private Double amount;

  @NotBlank(message = "Type not be blank")
  private String type;

  @NotBlank(message = "Note be not blank")
  private String note;

  @NotBlank(message = "Category not be blank")
  private Long categoryId;
}
