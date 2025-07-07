package com.learn.expense_tracker.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ApiResponse<T> {
  private String status;
  private T data;

  public ApiResponse(String status, T data) {
    this.status = status;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data);
  }
}
