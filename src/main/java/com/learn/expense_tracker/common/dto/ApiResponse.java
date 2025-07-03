package com.learn.expense_tracker.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ApiResponse<T> {
  private String status;
  private String message;
  private T data;

  public ApiResponse(String status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", "OK", data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>("success", message, data);
  }

  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>("error", message, null);
  }
}
